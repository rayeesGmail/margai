package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.TestcontainersConfiguration;
import com.margai.common.api.AttemptType;
import com.margai.common.api.Category;
import com.margai.curriculum.api.ArchetypeStepRow;
import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.BookSubject;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CurriculumImportException;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.CutoffRow;
import com.margai.curriculum.api.NcertBookRow;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.NcertRegisterReport;
import com.margai.curriculum.api.NcertVerificationRow;
import com.margai.curriculum.api.NodeKind;
import com.margai.curriculum.api.ParagraphEmbedding;
import com.margai.curriculum.api.ParagraphExtraction;
import com.margai.curriculum.api.ParagraphToEmbed;
import com.margai.curriculum.api.ParagraphVerification;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.SeatType;
import com.margai.curriculum.api.Subject;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import com.margai.curriculum.api.TrackPhase;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * The D13 loads end to end — the committed inputs through the readers into
 * {@link CurriculumImport} against a database of their own in the shared container (the
 * {@code importtest} profile has no seed): parents before children, idempotent re-runs, updates
 * counted, refusals that write nothing, the cycle check that PLAN D13's ✅ asks for, tracks whose
 * stale steps go, and cut-offs by natural key.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("importtest")
@Import(TestcontainersConfiguration.class)
class CurriculumImportTest {

    private static final Path TAXONOMY = InputReadersTest.INPUTS.resolve(TaxonomyLoadCommand.FILE);
    private static final Path PREREQUISITES = InputReadersTest.INPUTS.resolve(TaxonomyPrerequisitesCommand.FILE);
    private static final Path ARCHETYPES = InputReadersTest.INPUTS.resolve(BackboneLoadCommand.FILE);
    private static final Path CUTOFFS = InputReadersTest.INPUTS.resolve(CutoffsLoadCommand.FILE);
    private static final Path BOOKS = InputReadersTest.INPUTS.resolve(NcertRegisterCommand.FILE);

    @Autowired
    private CurriculumImport imports;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void emptyTheCurriculumTables() {
        jdbc.update("DELETE FROM syllabus_prerequisites");
        jdbc.update("DELETE FROM archetype_track_steps");
        jdbc.update("DELETE FROM archetype_tracks");
        jdbc.update("DELETE FROM chapter_status");
        jdbc.update("DELETE FROM ncert_paragraphs");
        jdbc.update("DELETE FROM ncert_books");
        jdbc.update("DELETE FROM syllabus_nodes");
        jdbc.update("DELETE FROM cutoffs");
    }

    @Test
    void registersTheCommittedBooksAndIsIdempotent() {
        List<NcertBookRow> rows = BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList();

        NcertRegisterReport first = imports.registerBooks(rows);
        assertThat(first.inserted()).isEqualTo(10);
        assertThat(first.updated()).isZero();
        assertThat(first.orphans()).isEmpty();

        NcertRegisterReport again = imports.registerBooks(rows);
        assertThat(again.inserted()).isZero();
        assertThat(again.unchanged()).isEqualTo(10);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_books", Long.class)).isEqualTo(10);
        assertThat(jdbc.queryForObject(
                "SELECT s3_key_en FROM ncert_books WHERE code = 'phy11-part2'", String.class))
                .isEqualTo("source/ncert/2022-ed/en/phy11-part2/");
        assertThat(jdbc.queryForObject("SELECT pages_en FROM ncert_books WHERE code = 'bio11'", Integer.class))
                .isNull();
    }

    @Test
    void aBookTheFileNoLongerNamesIsAnOrphanAndIsKept() {
        List<NcertBookRow> rows = BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList();
        imports.registerBooks(rows);

        NcertRegisterReport report = imports.registerBooks(rows.stream()
                .filter(row -> !row.code().equals("bio12")).toList());

        assertThat(report.orphans()).containsExactly("bio12");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_books", Long.class)).isEqualTo(10);
    }

    @Test
    void loadsParagraphsAtTheirAddressAndIsIdempotent() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());

        NcertLoadReport first = imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        assertThat(first.inserted()).isEqualTo(2);
        assertThat(first.perChapter()).containsEntry((short) 7, 2);

        NcertLoadReport again = imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        assertThat(again.inserted()).isZero();
        assertThat(again.unchanged()).isEqualTo(2);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_paragraphs", Long.class)).isEqualTo(2);
        assertThat(jdbc.queryForObject(
                "SELECT text_en FROM ncert_paragraphs WHERE chapter_no = 7 AND section = '7.9' AND para_no = 1",
                String.class))
                .isEqualTo("The gravitational potential energy of a body.");
    }

    /** D16's Hindi pass fills the same row rather than making a second one. */
    @Test
    void theHindiEditionLandsOnTheSameParagraphRow() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());

        imports.loadParagraphs("phy11-part1", BookLanguage.hi, List.of(new NcertParagraphRow((short) 7, "7.9",
                (short) 1, "गुरुत्वीय स्थितिज ऊर्जा।", false, List.of(),
                new ParagraphExtraction(List.of(12), new BigDecimal("0.90"), null))));

        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_paragraphs", Long.class)).isEqualTo(2);
        Map<String, Object> row = jdbc.queryForMap(
                "SELECT text_en, text_hi FROM ncert_paragraphs WHERE chapter_no = 7 AND section = '7.9' AND para_no = 1");
        assertThat(row.get("text_en")).isEqualTo("The gravitational potential energy of a body.");
        assertThat(row.get("text_hi")).isEqualTo("गुरुत्वीय स्थितिज ऊर्जा।");
    }

    /**
     * A re-extraction cuts paragraphs differently, and since v3 the loader numbers them, so a
     * one-page redo shifts every number after it. Rows at addresses the extraction no longer
     * carries were kept and reported through D14; by 2026-09-14 the table held 85 rows no run
     * produced beside the canonical 1,017, sampleable by the ✅ and embeddable by D17. They are
     * now deleted, and named (DECISIONS 2026-09-14).
     */
    @Test
    void paragraphsTheExtractionNoLongerCarriesAreDeletedAndNamed() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());

        NcertLoadReport report = imports.loadParagraphs("phy11-part1", BookLanguage.en,
                List.of(paragraphs().getFirst()));

        assertThat(report.orphans()).containsExactly("ch 7 §7.9 ¶2");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_paragraphs", Long.class)).isEqualTo(1);
    }

    /**
     * A load of one chapter says nothing about the others. The first `--chapters 7` load into a
     * book that already held chapters 1–6 reported every one of their addresses as "no longer
     * carried" — 600 lines, all wrong (D14). Orphans are judged, and now deleted, only within the
     * chapters loaded.
     */
    @Test
    void aChapterSubsetLoadPrunesOrphansOnlyWithinItsOwnChapters() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());

        NcertLoadReport report = imports.loadParagraphs("phy11-part1", BookLanguage.en, List.of(
                new NcertParagraphRow((short) 8, "8.1", (short) 1, "Another chapter entirely.", false, List.of(),
                        new ParagraphExtraction(List.of(1), new BigDecimal("0.95"), null))));

        assertThat(report.orphans()).as("chapter 7's rows are not this load's business").isEmpty();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_paragraphs", Long.class)).isEqualTo(3);
    }

    /**
     * From D23 a question anchors to a paragraph id, and from D17 a row carries an embedding; a
     * load that deleted such a row would re-point a question at nothing. Until the D17 migrate
     * path exists, a load that would prune an anchored row refuses the whole book by name, and
     * writes nothing (TRACKER PARKED "freeze-and-migrate guard", DECISIONS 2026-09-14).
     */
    @Test
    void anOrphanThatIsAnchoredRefusesTheLoadAndWritesNothing() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        loadTaxonomy();
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        jdbc.update("UPDATE ncert_paragraphs SET node_id = (SELECT id FROM syllabus_nodes LIMIT 1) "
                + "WHERE chapter_no = 7 AND section = '7.9' AND para_no = 2");

        // The row this load does carry is changed, so the refusal has an update to roll back as
        // well as a deletion to withhold — "nothing was written" means both.
        NcertParagraphRow changed = new NcertParagraphRow((short) 7, "7.9", (short) 1,
                "A changed first paragraph.", false, List.of(),
                new ParagraphExtraction(List.of(12), new BigDecimal("0.96"), null));

        assertThatThrownBy(() -> imports.loadParagraphs("phy11-part1", BookLanguage.en, List.of(changed)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("ch 7 §7.9 ¶2")
                .hasMessageContaining("anchored");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_paragraphs", Long.class)).isEqualTo(2);
        assertThat(jdbc.queryForObject(
                "SELECT text_en FROM ncert_paragraphs WHERE chapter_no = 7 AND section = '7.9' AND para_no = 1",
                String.class))
                .isEqualTo("The gravitational potential energy of a body.");
    }

    @Test
    void loadingIntoAnUnregisteredBookIsRefused() {
        assertThatThrownBy(() -> imports.loadParagraphs("bio11", BookLanguage.en, paragraphs()))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("run `ncert register` first");
    }

    @Test
    void oneAddressTwiceInAnExtractionIsRefused() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        NcertParagraphRow row = paragraphs().getFirst();

        assertThatThrownBy(() -> imports.loadParagraphs("phy11-part1", BookLanguage.en, List.of(row, row)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("the address ch 7 §7.9 ¶1 appears twice");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_paragraphs", Long.class)).isZero();
    }

    /**
     * `ncert verify --read-pages` reads the rows it checks back through the door it loaded them
     * through, with the offsets that say which characters each page printed (D15): a paragraph
     * straddling a page break is judged against each page for the part that page carries.
     */
    @Test
    void readsBackTheEditionsRowsWithTheirPageStarts() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, List.of(
                new NcertParagraphRow((short) 8, "8.1", (short) 1, "Another chapter entirely.", false, List.of(),
                        new ParagraphExtraction(List.of(1), new BigDecimal("0.95"), null))));

        List<NcertParagraphRow> rows = imports.paragraphs("phy11-part1", BookLanguage.en, List.of((short) 7));

        assertThat(rows).extracting(NcertParagraphRow::address).containsExactly("ch 7 §7.9 ¶1", "ch 7 §7.9 ¶2");
        assertThat(rows.get(1).extraction().pages()).containsExactly(12, 13);
        assertThat(rows.get(1).extraction().pageStarts()).containsExactly(0, 14);
        assertThat(rows.get(1).figureRefs()).containsExactly("Fig. 7.9");
    }

    /** Every row loaded through 2026-09-14 carries the old shape; reading it must not fail, only say it has no offsets. */
    @Test
    void aRowLoadedBeforePageStartsExistedReadsBackWithoutThem() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        jdbc.update("UPDATE ncert_paragraphs SET extraction = "
                + "'{\"en\": {\"pages\": [12], \"aiCallId\": \"6b610845-8fc9-495e-9d09-07fa8bfbc532\", \"confidence\": 0.9}}'::jsonb");

        List<NcertParagraphRow> rows = imports.paragraphs("phy11-part1", BookLanguage.en, List.of((short) 7));

        assertThat(rows).allSatisfy(row -> {
            assertThat(row.extraction().pageStarts()).isEmpty();
            assertThat(row.extraction().verification()).isNull();
        });
    }

    @Test
    void recordsAVerdictOnTheRowItNames() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());

        int recorded = imports.recordVerifications("phy11-part1", BookLanguage.en, List.of(
                verdictFor(paragraphs().get(1), ParagraphVerification.Verdict.differs)));

        assertThat(recorded).isEqualTo(1);
        ParagraphVerification stored = imports.paragraphs("phy11-part1", BookLanguage.en, List.of((short) 7))
                .get(1).extraction().verification();
        assertThat(stored.verdict()).isEqualTo(ParagraphVerification.Verdict.differs);
        assertThat(stored.differences()).containsExactly(new ParagraphVerification.Difference(12, "-G", "G"));
        assertThat(stored.model()).isEqualTo("claude-sonnet-5");
    }

    /** Reloading the frozen run must not cost a re-verification: the verdict is about the text, which did not move. */
    @Test
    void reloadingTheSameTextKeepsTheVerdict() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        imports.recordVerifications("phy11-part1", BookLanguage.en, List.of(
                verdictFor(paragraphs().getFirst(), ParagraphVerification.Verdict.matches)));

        NcertLoadReport again = imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());

        assertThat(again.unchanged()).isEqualTo(2);
        assertThat(imports.paragraphs("phy11-part1", BookLanguage.en, List.of((short) 7))
                .getFirst().extraction().verification().verdict())
                .isEqualTo(ParagraphVerification.Verdict.matches);
    }

    /** A verdict on words the row no longer holds is a verdict on nothing: a correction drops it. */
    @Test
    void aChangedTextDropsTheVerdict() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        imports.recordVerifications("phy11-part1", BookLanguage.en, List.of(
                verdictFor(paragraphs().getFirst(), ParagraphVerification.Verdict.differs)));

        NcertLoadReport corrected = imports.loadParagraphs("phy11-part1", BookLanguage.en, List.of(
                new NcertParagraphRow((short) 7, "7.9", (short) 1, "The gravitational potential energy of a body!",
                        false, List.of(), new ParagraphExtraction(List.of(12), new BigDecimal("0.96"), null)),
                paragraphs().get(1)));

        assertThat(corrected.updated()).isEqualTo(1);
        assertThat(imports.paragraphs("phy11-part1", BookLanguage.en, List.of((short) 7))
                .getFirst().extraction().verification()).isNull();
    }

    @Test
    void aVerdictOnTextTheRowNoLongerHoldsIsRefusedAndWritesNothing() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        NcertParagraphRow stale = new NcertParagraphRow((short) 7, "7.9", (short) 2, "W = G M m / r.", true,
                List.of(), paragraphs().get(1).extraction());

        assertThatThrownBy(() -> imports.recordVerifications("phy11-part1", BookLanguage.en, List.of(
                verdictFor(paragraphs().getFirst(), ParagraphVerification.Verdict.matches),
                verdictFor(stale, ParagraphVerification.Verdict.matches))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("ch 7 §7.9 ¶2")
                .hasMessageContaining("text has changed since it was verified");
        assertThat(imports.paragraphs("phy11-part1", BookLanguage.en, List.of((short) 7)))
                .allSatisfy(row -> assertThat(row.extraction().verification()).isNull());
    }

    @Test
    void aVerdictForAnAddressTheBookDoesNotHaveIsRefused() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        NcertParagraphRow elsewhere = new NcertParagraphRow((short) 7, "7.10", (short) 4, "Nowhere.", false,
                List.of(), paragraphs().getFirst().extraction());

        assertThatThrownBy(() -> imports.recordVerifications("phy11-part1", BookLanguage.en, List.of(
                verdictFor(elsewhere, ParagraphVerification.Verdict.matches))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("ch 7 §7.10 ¶4")
                .hasMessageContaining("not in phy11-part1");
    }

    private static NcertVerificationRow verdictFor(NcertParagraphRow row, ParagraphVerification.Verdict verdict) {
        return new NcertVerificationRow(row.chapterNo(), row.section(), row.paraNo(), new ParagraphVerification(
                verdict,
                verdict == ParagraphVerification.Verdict.differs
                        ? List.of(new ParagraphVerification.Difference(12, "-G", "G")) : List.of(),
                ParagraphVerification.sha256(row.text()), List.of(), "claude-sonnet-5", "ncert_verify.v1"));
    }

    // ── `ncert embed` (D15): the embedding column, whose null is the whole state machine ──────────

    @Test
    void everyParagraphWithEnglishTextWaitsToBeEmbeddedUntilItHasAVector() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());

        List<ParagraphToEmbed> waiting = imports.paragraphsToEmbed("phy11-part1", List.of(), false);
        assertThat(waiting).extracting(ParagraphToEmbed::address)
                .containsExactly("ch 7 §7.9 ¶1", "ch 7 §7.9 ¶2");
        assertThat(waiting.getFirst().text()).isEqualTo("The gravitational potential energy of a body.");

        imports.storeEmbeddings("phy11-part1", List.of(
                new ParagraphEmbedding(waiting.getFirst().paragraphId(), vector(0.1f))));

        assertThat(imports.paragraphsToEmbed("phy11-part1", List.of(), false)).extracting(ParagraphToEmbed::address)
                .as("an embedded paragraph is not waiting any more").containsExactly("ch 7 §7.9 ¶2");
        assertThat(imports.paragraphsToEmbed("phy11-part1", List.of(), true)).as("--redo takes the whole book")
                .hasSize(2);
    }

    /** The stored vector is the stored vector — 1,024 floats in, the same 1,024 out. */
    @Test
    void theVectorRoundTripsThroughThePgvectorColumn() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        ParagraphToEmbed first = imports.paragraphsToEmbed("phy11-part1", List.of(), false).getFirst();
        float[] stored = vector(0.25f);

        assertThat(imports.storeEmbeddings("phy11-part1", List.of(
                new ParagraphEmbedding(first.paragraphId(), stored)))).isEqualTo(1);

        String readBack = jdbc.queryForObject(
                "SELECT embedding::text FROM ncert_paragraphs WHERE id = ?", String.class, first.paragraphId());
        assertThat(readBack).startsWith("[0.25,").endsWith("]");
        assertThat(readBack.split(",")).as("every dimension survived").hasSize(1024);
    }

    /**
     * A vector on the wrong paragraph is a wrong anchor under every answer that retrieves it, and
     * unlike a wrong transcription nobody can see it by reading the row. So the batch is checked
     * before any of it is written.
     */
    @Test
    void anEmbeddingForAParagraphOfAnotherBookIsRefusedAndWritesNothing() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        List<ParagraphToEmbed> waiting = imports.paragraphsToEmbed("phy11-part1", List.of(), false);
        UUID foreign = UUID.randomUUID();

        assertThatThrownBy(() -> imports.storeEmbeddings("phy11-part1", List.of(
                new ParagraphEmbedding(waiting.getFirst().paragraphId(), vector(0.1f)),
                new ParagraphEmbedding(foreign, vector(0.2f)))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining(foreign.toString())
                .hasMessageContaining("nothing was written");

        assertThat(jdbc.queryForObject(
                "SELECT count(*) FROM ncert_paragraphs WHERE embedding IS NOT NULL", Long.class)).isZero();
    }

    /**
     * The staleness half of the same mechanism (D15): a load that rewrites a paragraph's words
     * drops the vector that described the old ones, so `ncert embed` picks the row up again.
     * Without this the row would keep a vector for text it was corrected away from, and no report
     * anywhere would say so.
     */
    @Test
    void aLoadThatRewritesTheTextClearsThatParagraphsEmbedding() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        embedEverything();

        NcertParagraphRow corrected = new NcertParagraphRow((short) 7, "7.9", (short) 1,
                "The gravitational potential energy of a body, corrected.", false, List.of(),
                paragraphs().getFirst().extraction());
        NcertLoadReport report = imports.loadParagraphs("phy11-part1", BookLanguage.en,
                List.of(corrected, paragraphs().get(1)));

        assertThat(report.embeddingsCleared()).isEqualTo(1);
        assertThat(imports.paragraphsToEmbed("phy11-part1", List.of(), false)).extracting(ParagraphToEmbed::address)
                .containsExactly("ch 7 §7.9 ¶1");
    }

    /** An idempotent re-load changes no words, so the book stays embedded and nothing is re-paid. */
    @Test
    void anIdempotentReloadClearsNoEmbeddings() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        embedEverything();

        NcertLoadReport again = imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());

        assertThat(again.embeddingsCleared()).isZero();
        assertThat(imports.paragraphsToEmbed("phy11-part1", List.of(), false)).isEmpty();
    }

    /**
     * An embedding is a property of the row, not a pointer to it, so an embedded orphan is deleted
     * like any other — the vector described words nobody carries any more. It is counted, because
     * a load quietly throwing away paid work should be visible in the report.
     */
    @Test
    void anEmbeddedParagraphTheExtractionDropsIsDeletedAndCounted() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.en, paragraphs());
        embedEverything();

        NcertLoadReport report = imports.loadParagraphs("phy11-part1", BookLanguage.en,
                List.of(paragraphs().getFirst()));

        assertThat(report.orphans()).containsExactly("ch 7 §7.9 ¶2");
        assertThat(report.embeddedOrphans()).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_paragraphs", Long.class)).isEqualTo(1);
    }

    /** §6.4 embeds `text_en` and nothing else, so a Hindi-only row is not waiting for a vector. */
    @Test
    void aParagraphWithNoEnglishTextIsNotWaitingToBeEmbedded() {
        imports.registerBooks(BooksYamlReader.read(BOOKS).stream().map(BookDefinition::row).toList());
        imports.loadParagraphs("phy11-part1", BookLanguage.hi, List.of(new NcertParagraphRow((short) 7, "7.9",
                (short) 1, "गुरुत्वीय स्थितिज ऊर्जा।", false, List.of(),
                new ParagraphExtraction(List.of(12), new BigDecimal("0.90"), null))));

        assertThat(imports.paragraphsToEmbed("phy11-part1", List.of(), false)).isEmpty();
    }

    @Test
    void embeddingAnUnregisteredBookIsRefused() {
        assertThatThrownBy(() -> imports.paragraphsToEmbed("nosuchbook", List.of(), false))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("is not registered");
    }

    private void embedEverything() {
        imports.storeEmbeddings("phy11-part1", imports.paragraphsToEmbed("phy11-part1", List.of(), true).stream()
                .map(waiting -> new ParagraphEmbedding(waiting.paragraphId(), vector(0.1f)))
                .toList());
    }

    /** A 1,024-wide vector, the pinned width `EmbeddingDimensionTest` holds the column to. */
    private static float[] vector(float first) {
        float[] values = new float[1024];
        values[0] = first;
        return values;
    }

    private void loadTaxonomy() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
    }

    private static List<NcertParagraphRow> paragraphs() {
        return List.of(
                new NcertParagraphRow((short) 7, "7.9", (short) 1,
                        "The gravitational potential energy of a body.", false, List.of(),
                        new ParagraphExtraction(List.of(12), new BigDecimal("0.96"), null)),
                new NcertParagraphRow((short) 7, "7.9", (short) 2,
                        "W = -G M m / r (Fig. 7.9).", true, List.of("Fig. 7.9"),
                        new ParagraphExtraction(List.of(12, 13), List.of(0, 14), new BigDecimal("0.91"), null, null)));
    }

    @Test
    void twoBooksMayNotClaimTheSameEdition() {
        NcertBookRow bio11 = new NcertBookRow("bio11", BookSubject.biology, (short) 11, null,
                "Biology, Textbook for Class XI", null, (short) 2022, "source/a/", null);
        NcertBookRow duplicate = new NcertBookRow("bio11-reprint", BookSubject.biology, (short) 11, null,
                "Biology, Textbook for Class XI", null, (short) 2022, "source/b/", null);

        assertThatThrownBy(() -> imports.registerBooks(List.of(bio11, duplicate)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageContaining("two books claim to be biology class 11 part null");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM ncert_books", Long.class)).isZero();
    }

    @Test
    void loadsTheCommittedTaxonomyParentsFirst() {
        TaxonomyLoadReport report = imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));

        assertThat(report.inserted()).isEqualTo(516);
        assertThat(report.updated()).isZero();
        assertThat(report.unchanged()).isZero();
        assertThat(report.orphans()).isEmpty();
        assertThat(report.counts().get(Subject.physics))
                .containsEntry(NodeKind.unit, 20).containsEntry(NodeKind.chapter, 29).containsEntry(NodeKind.topic, 134);
        assertThat(report.counts().get(Subject.zoology)).containsEntry(NodeKind.unit, 6).containsEntry(NodeKind.chapter, 12);

        assertThat(count("kind = 'subject'")).isEqualTo(4);
        assertThat(count("kind = 'unit'")).isEqualTo(55);
        assertThat(count("kind = 'chapter'")).isEqualTo(83);
        assertThat(count("kind = 'topic'")).isEqualTo(374);
        assertThat(count("parent_id IS NULL")).isEqualTo(4);
        assertThat(parentCodeOf("PHY.11.GRAV.KEPLER")).isEqualTo("PHY.11.GRAV");
        assertThat(parentCodeOf("PHY.11.GRAV")).isEqualTo("PHY.U06");
        assertThat(parentCodeOf("PHY.U06")).isEqualTo("PHY");
        assertThat(jdbc.queryForObject("SELECT default_learn_minutes FROM syllabus_nodes WHERE code = 'PHY.11.GRAV'", Integer.class))
                .isEqualTo(225);
        assertThat(jdbc.queryForObject("SELECT class_level FROM syllabus_nodes WHERE code = 'PHY.00.EXPSKILL'", Integer.class))
                .isNull();
    }

    @Test
    void aSecondRunChangesNothing() {
        List<SyllabusNodeRow> rows = TaxonomyCsvReader.read(TAXONOMY);
        imports.loadTaxonomy(rows);

        TaxonomyLoadReport again = imports.loadTaxonomy(rows);

        assertThat(again.inserted()).isZero();
        assertThat(again.updated()).isZero();
        assertThat(again.unchanged()).isEqualTo(516);
        assertThat(count("TRUE")).isEqualTo(516);
    }

    @Test
    void aChangedRowIsAnUpdateAndKeepsItsId() {
        List<SyllabusNodeRow> rows = TaxonomyCsvReader.read(TAXONOMY);
        imports.loadTaxonomy(rows);
        String idBefore = jdbc.queryForObject("SELECT id::text FROM syllabus_nodes WHERE code = 'PHY.11.GRAV'", String.class);

        TaxonomyLoadReport report = imports.loadTaxonomy(rows.stream()
                .map(row -> row.code().equals("PHY.11.GRAV") ? renamed(row, "Gravitation (renamed)") : row)
                .toList());

        assertThat(report.updated()).isEqualTo(1);
        assertThat(report.unchanged()).isEqualTo(515);
        assertThat(jdbc.queryForObject("SELECT name_en FROM syllabus_nodes WHERE code = 'PHY.11.GRAV'", String.class))
                .isEqualTo("Gravitation (renamed)");
        assertThat(jdbc.queryForObject("SELECT id::text FROM syllabus_nodes WHERE code = 'PHY.11.GRAV'", String.class))
                .isEqualTo(idBefore);
    }

    @Test
    void aParentMissingFromTheFileOrOfTheWrongKindWritesNothing() {
        SyllabusNodeRow physics = new SyllabusNodeRow("PHY", Subject.physics, null, null, NodeKind.subject, "Physics", null, 1, null, true);
        SyllabusNodeRow orphanChapter = new SyllabusNodeRow("PHY.11.UNITS", Subject.physics, (short) 11, "PHY.U01",
                NodeKind.chapter, "Units", null, 1, null, true);
        SyllabusNodeRow chapterUnderSubject = new SyllabusNodeRow("PHY.11.UNITS", Subject.physics, (short) 11, "PHY",
                NodeKind.chapter, "Units", null, 1, null, true);

        assertThatThrownBy(() -> imports.loadTaxonomy(List.of(physics, orphanChapter)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("parent 'PHY.U01' of PHY.11.UNITS is not in the file");
        assertThatThrownBy(() -> imports.loadTaxonomy(List.of(physics, chapterUnderSubject)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("the parent of a chapter must be a unit; PHY.11.UNITS hangs from the subject PHY");
        assertThat(count("TRUE")).isZero();
    }

    @Test
    void loadsThePrerequisitesAndFindsNoCycle() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        List<PrerequisiteRow> edges = PrerequisitesCsvReader.read(PREREQUISITES);

        PrerequisiteLoadReport first = imports.loadPrerequisites(edges);
        PrerequisiteLoadReport again = imports.loadPrerequisites(edges);

        assertThat(first.inserted()).isEqualTo(104);
        assertThat(first.unchanged()).isZero();
        assertThat(first.edgesInDatabase()).isEqualTo(104);
        assertThat(first.nodesWithEdges()).isEqualTo(83);
        assertThat(first.orphanEdges()).isEmpty();
        assertThat(again.inserted()).isZero();
        assertThat(again.unchanged()).isEqualTo(104);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM syllabus_prerequisites", Long.class)).isEqualTo(104);
    }

    @Test
    void aCycleRollsTheRunBack() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        imports.loadPrerequisites(PrerequisitesCsvReader.read(PREREQUISITES));

        assertThatThrownBy(() -> imports.loadPrerequisites(List.of(new PrerequisiteRow("ZOO.11.ANIMALK", "BOT.11.CLASSIF"))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessageStartingWith("the prerequisite graph has a cycle among ")
                .hasMessageContaining("BOT.11.CLASSIF")
                .hasMessageContaining("ZOO.11.ANIMALK");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM syllabus_prerequisites", Long.class)).isEqualTo(104);
    }

    @Test
    void unknownAndNonChapterEndpointsAreRefused() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));

        assertThatThrownBy(() -> imports.loadPrerequisites(List.of(new PrerequisiteRow("PHY.11.UNITS", "PHY.11.NOSUCH"))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("prerequisite endpoints not in the taxonomy: [PHY.11.NOSUCH]");
        assertThatThrownBy(() -> imports.loadPrerequisites(List.of(new PrerequisiteRow("PHY.U01", "PHY.11.KIN1D"))))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("prerequisites are chapter-level (TECH_PLAN §2.3); not chapters: [PHY.U01]");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM syllabus_prerequisites", Long.class)).isZero();
    }

    @Test
    void loadsTheBackboneAndEveryChapterIsLearnedByATrack() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        List<ArchetypeTrackRow> tracks = ArchetypesYamlReader.read(ARCHETYPES);

        BackboneLoadReport first = imports.loadBackbone(tracks);
        BackboneLoadReport again = imports.loadBackbone(tracks);

        assertThat(first.tracksInserted()).isEqualTo(4);
        assertThat(first.stepsInserted()).isEqualTo(744);
        assertThat(first.stepsRemoved()).isZero();
        assertThat(first.stepsPerTrack())
                .containsEntry(AttemptType.fresher_2yr, 210).containsEntry(AttemptType.fresher_1yr, 166)
                .containsEntry(AttemptType.dropper, 178).containsEntry(AttemptType.repeater, 190);
        assertThat(first.nodesInNoTrack()).isEmpty();
        assertThat(first.orphanTracks()).isEmpty();
        assertThat(again.tracksUnchanged()).isEqualTo(4);
        assertThat(again.stepsUnchanged()).isEqualTo(744);
        assertThat(again.stepsInserted()).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM archetype_track_steps", Long.class)).isEqualTo(744);
        assertThat(jdbc.queryForObject(
                "SELECT n.code FROM archetype_track_steps s JOIN archetype_tracks t ON t.id = s.track_id"
                        + " JOIN syllabus_nodes n ON n.id = s.node_id WHERE t.code = 'dropper' AND s.sequence = 1",
                String.class)).isEqualTo("PHY.11.UNITS");
        assertThat(jdbc.queryForObject("SELECT weeks FROM archetype_tracks WHERE code = 'fresher_2yr'", Integer.class))
                .isEqualTo(96);
    }

    @Test
    void aShortenedTrackLosesItsStaleStepsAndAChangedStepIsAnUpdate() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        List<ArchetypeTrackRow> tracks = ArchetypesYamlReader.read(ARCHETYPES);
        imports.loadBackbone(tracks);
        // The dropper's first 50 steps learn about half the chapters; the file's last learn step is sequence 95.
        ArchetypeTrackRow dropper = tracks.get(2);
        List<ArchetypeStepRow> kept = dropper.steps().subList(0, 50).stream()
                .map(step -> step.sequence() == 1 ? new ArchetypeStepRow(1, step.nodeCode(), step.phase(), (short) 2) : step)
                .toList();
        ArchetypeTrackRow shortened = new ArchetypeTrackRow(dropper.code(), dropper.nameEn(), dropper.nameHi(),
                dropper.weeks(), dropper.descriptionMd(), kept);

        BackboneLoadReport report = imports.loadBackbone(List.of(shortened));

        assertThat(report.tracksUnchanged()).isEqualTo(1);
        assertThat(report.stepsUpdated()).isEqualTo(1);
        assertThat(report.stepsUnchanged()).isEqualTo(49);
        assertThat(report.stepsRemoved()).isEqualTo(128);
        // Chapters the first 50 steps never learn, and every unit: revision steps start at sequence 100.
        assertThat(report.nodesInNoTrack()).contains("CHE.00.PRACTICAL", "BOT.12.MICROBES", "PHY.U01", "ZOO.U08")
                .doesNotContain("PHY", "CHE", "BOT", "ZOO", "PHY.11.UNITS");
        assertThat(report.orphanTracks()).containsExactly("fresher_1yr", "fresher_2yr", "repeater");
        assertThat(jdbc.queryForObject(
                "SELECT count(*) FROM archetype_track_steps s JOIN archetype_tracks t ON t.id = s.track_id WHERE t.code = 'dropper'",
                Long.class)).isEqualTo(50);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM archetype_track_steps", Long.class)).isEqualTo(744 - 128);
    }

    @Test
    void aStepNamingTheWrongKindOfNodeOrAnUnknownNodeIsRefused() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        ArchetypeTrackRow learnsAUnit = track(new ArchetypeStepRow(1, "PHY.U01", TrackPhase.learn, (short) 1));
        ArchetypeTrackRow mocksAChapter = track(new ArchetypeStepRow(1, "PHY.11.UNITS", TrackPhase.mock, (short) 1));
        ArchetypeTrackRow unknown = track(new ArchetypeStepRow(1, "PHY.11.NOSUCH", TrackPhase.learn, (short) 1));

        assertThatThrownBy(() -> imports.loadBackbone(List.of(learnsAUnit)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track dropper step 1: a learn step names a chapter, not the unit PHY.U01");
        assertThatThrownBy(() -> imports.loadBackbone(List.of(mocksAChapter)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track dropper step 1: a mock step names a subject, not the chapter PHY.11.UNITS");
        assertThatThrownBy(() -> imports.loadBackbone(List.of(unknown)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track steps name nodes not in the taxonomy: [PHY.11.NOSUCH]");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM archetype_tracks", Long.class)).isZero();
    }

    @Test
    void aDuplicateSiblingSortOrderAndATopicClassMismatchAreRefused() {
        SyllabusNodeRow physics = new SyllabusNodeRow("PHY", Subject.physics, null, null, NodeKind.subject, "Physics", null, 1, null, true);
        SyllabusNodeRow unit1 = new SyllabusNodeRow("PHY.U01", Subject.physics, null, "PHY", NodeKind.unit, "Measurement", null, 1, null, true);
        SyllabusNodeRow unit2SameOrder = new SyllabusNodeRow("PHY.U02", Subject.physics, null, "PHY", NodeKind.unit, "Kinematics", null, 1, null, true);
        SyllabusNodeRow chapter = new SyllabusNodeRow("PHY.11.UNITS", Subject.physics, (short) 11, "PHY.U01", NodeKind.chapter, "Units", null, 1, null, true);
        SyllabusNodeRow topicOfClass12 = new SyllabusNodeRow("PHY.11.UNITS.SI", Subject.physics, (short) 12, "PHY.11.UNITS", NodeKind.topic, "SI units", null, 1, 45, true);

        assertThatThrownBy(() -> imports.loadTaxonomy(List.of(physics, unit1, unit2SameOrder)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("sort_order 1 appears twice under PHY (PHY.U01 and PHY.U02)");
        assertThatThrownBy(() -> imports.loadTaxonomy(List.of(physics, unit1, chapter, topicOfClass12)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("topic PHY.11.UNITS.SI has class_level 12 but its chapter PHY.11.UNITS has 11");
        assertThat(count("TRUE")).isZero();
    }

    @Test
    void aChapterLearnedTwiceOrBeforeItsPrerequisiteIsRefused() {
        imports.loadTaxonomy(TaxonomyCsvReader.read(TAXONOMY));
        imports.loadPrerequisites(PrerequisitesCsvReader.read(PREREQUISITES));
        ArchetypeTrackRow twice = track(
                new ArchetypeStepRow(1, "PHY.11.UNITS", TrackPhase.learn, (short) 1),
                new ArchetypeStepRow(2, "PHY.11.UNITS", TrackPhase.learn, (short) 2));
        ArchetypeTrackRow outOfOrder = track(
                new ArchetypeStepRow(1, "PHY.11.KIN1D", TrackPhase.learn, (short) 1),
                new ArchetypeStepRow(2, "PHY.11.UNITS", TrackPhase.learn, (short) 1));

        assertThatThrownBy(() -> imports.loadBackbone(List.of(twice)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track dropper: chapter PHY.11.UNITS is learned twice (sequences 1 and 2)");
        assertThatThrownBy(() -> imports.loadBackbone(List.of(outOfOrder)))
                .isInstanceOf(CurriculumImportException.class)
                .hasMessage("track dropper: PHY.11.KIN1D (sequence 1) is learned before its prerequisite PHY.11.UNITS (sequence 2)");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM archetype_tracks", Long.class)).isZero();
    }

    @Test
    void loadsTheCutoffsByNaturalKey() {
        List<CutoffRow> rows = CutoffsCsvReader.read(CUTOFFS);

        CutoffLoadReport first = imports.loadCutoffs(rows);
        CutoffLoadReport again = imports.loadCutoffs(rows);
        CutoffLoadReport changed = imports.loadCutoffs(rows.stream()
                .map(row -> row.year() == 2026 && row.category() == Category.general
                        ? new CutoffRow(row.year(), row.category(), row.quotaScope(), row.seatType(), (short) 214, row.source())
                        : row)
                .toList());

        assertThat(first.inserted()).isEqualTo(40);
        assertThat(first.rowsPerYear()).containsEntry((short) 2019, 5).containsEntry((short) 2026, 5).hasSize(8);
        assertThat(first.orphans()).isEmpty();
        assertThat(again.unchanged()).isEqualTo(40);
        assertThat(changed.updated()).isEqualTo(1);
        assertThat(changed.unchanged()).isEqualTo(39);
        assertThat(jdbc.queryForObject(
                "SELECT qualifying_marks FROM cutoffs WHERE year = 2026 AND category = 'general' AND seat_type = 'qualifying'",
                Integer.class)).isEqualTo(214);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM cutoffs", Long.class)).isEqualTo(40);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM cutoffs WHERE seat_type = 'qualifying'", Long.class)).isEqualTo(40);
    }

    private long count(String where) {
        return jdbc.queryForObject("SELECT count(*) FROM syllabus_nodes WHERE " + where, Long.class);
    }

    private String parentCodeOf(String code) {
        return jdbc.queryForObject(
                "SELECT p.code FROM syllabus_nodes n JOIN syllabus_nodes p ON p.id = n.parent_id WHERE n.code = ?",
                String.class, code);
    }

    private static SyllabusNodeRow renamed(SyllabusNodeRow row, String nameEn) {
        return new SyllabusNodeRow(row.code(), row.subject(), row.classLevel(), row.parentCode(), row.kind(), nameEn,
                row.nameHi(), row.sortOrder(), row.defaultLearnMinutes(), row.neetRelevant());
    }

    private static ArchetypeTrackRow track(ArchetypeStepRow... steps) {
        return new ArchetypeTrackRow(AttemptType.dropper, "Dropper (1st repeat)", null, (short) 40, null, List.of(steps));
    }
}
