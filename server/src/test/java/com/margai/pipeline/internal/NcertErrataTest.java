package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.margai.curriculum.api.AlignmentRow;
import com.margai.curriculum.api.BookLanguage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * The errata log ({@code pipeline/inputs/ncert-errata.yaml}, D16, DECISIONS 2026-10-17) and {@code ncert errata},
 * which checks it against the loaded rows: every printing error a row corrects, and every slip it keeps that is
 * an errata candidate, named by the row's address.
 */
class NcertErrataTest {

    /** bio11 Hindi §19.2.8 ¶2: the page prints पेट (stomach) where glucagon stimulates gluconeogenesis. */
    private static final String ENTRY = """
            errata:
              - {book: bio11, lang: hi, chapter: 19, section: "19.2.8", para: 2, page: 8,
                 printed: "पेट", corrected: "ग्लूकागॉन", round: "bio11 Hindi round one, approved 2026-10-17 (ec40175)",
                 meaning_changing: true, action: corrected, flag_reviewed: false,
                 reason: "the page names the stomach as what stimulates gluconeogenesis"}
            """;

    /** phy12-part2 English §12.6 ¶4: Bohr's quantum condition cited as Eq. (12.15), which is Eq. (12.5). */
    private static final String KEPT = """
              - {book: phy12-part2, lang: en, chapter: 12, section: "12.6", para: 4, page: 13,
                 printed: "[Eq. (12.15)]", corrected: "[Eq. (12.5)]", round: "parked 2026-10-16",
                 meaning_changing: false, action: kept, flag_reviewed: false,
                 reason: "a wrong cross-reference, kept: the English rows are frozen"}
            """;

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final PipelineCommandTest.RecordingImport imports = new PipelineCommandTest.RecordingImport();

    @Test
    void readsAnEntryWithEveryField() throws IOException {
        List<NcertErratum> errata = NcertErrataYamlReader.read(write(ENTRY));

        assertThat(errata).containsExactly(new NcertErratum("bio11", BookLanguage.hi, (short) 19, "19.2.8", (short) 2, 8,
                "पेट", "ग्लूकागॉन", "the page names the stomach as what stimulates gluconeogenesis",
                "bio11 Hindi round one, approved 2026-10-17 (ec40175)", true, NcertErratum.Action.corrected, false));
        assertThat(errata.getFirst().address()).isEqualTo("ch 19 §19.2.8 ¶2");
        assertThat(errata.getFirst().carried()).isEqualTo("ग्लूकागॉन");
        assertThat(NcertErrataYamlReader.read(write(ENTRY + KEPT)).getLast().carried()).isEqualTo("[Eq. (12.15)]");
        assertThat(NcertErrataYamlReader.read(write("errata: []\n"))).isEmpty();
    }

    @Test
    void refusesAnEntryOfTheWrongShapeByNumber() throws IOException {
        assertRefused(ENTRY.replace("flag_reviewed: false,", "flag_reviewed: false, address: x,"),
                "erratum 1: unknown key 'address'");
        assertRefused(ENTRY.replace(" flag_reviewed: false,", ""), "erratum 1: 'flag_reviewed' must be true or false");
        assertRefused(ENTRY.replace("meaning_changing: true", "meaning_changing: yes please"),
                "erratum 1: 'meaning_changing' must be true or false");
        assertRefused(ENTRY.replace("action: corrected", "action: fixed"), "erratum 1: action must be one of");
        assertRefused(ENTRY.replace("section: \"19.2.8\"", "section: 19.2"), "erratum 1: 'section' must be quoted");
        assertRefused(ENTRY.replace("\"19.2.8\"", "\"18.2\""), "erratum 1: '18.2' is not a section of chapter 19");
        assertRefused(ENTRY.replace("\"ग्लूकागॉन\"", "\"पेट\""), "erratum 1: 'printed' and 'corrected' are the same");
        assertRefused(ENTRY.replace("printed: \"पेट\"", "printed: 1.10"), "erratum 1: 'printed' must be quoted");
        assertRefused(ENTRY.replace(" round: \"bio11 Hindi round one, approved 2026-10-17 (ec40175)\",", ""),
                "erratum 1: 'round' is required");
        assertRefused("corrections: []\n", "unknown top-level key 'corrections'");
    }

    @Test
    void passesWhenEveryRowCarriesItsSpanOnItsPage() throws IOException {
        Files.writeString(inputs.resolve(NcertErrataYamlReader.FILE), ENTRY + KEPT);
        imports.alignmentByBook = Map.of(
                "bio11", List.of(hindi(19, "19.2.8", 2, "इसके अतिरिक्त ग्लूकागॉन ग्लूकोनियोजिनेसिस को प्रेरित करता है।", 8)),
                "phy12-part2", List.of(english(12, "12.6", 4,
                        "This is the quantum condition proposed by Bohr [Eq. (12.15)].", 13)));

        assertThat(run()).isZero();
        assertThat(out.toString())
                .contains("- read: 2 errata over 2 book(s)\n- result: ok\n")
                .contains("| book | lang | corrected | kept | meaning_changing | flag not reviewed | not borne out |\n"
                        + "|---|---|---|---|---|---|---|\n| bio11 | hi | 1 | 0 | 1 | 1 | 0 |\n"
                        + "| phy12-part2 | en | 0 | 1 | 0 | 1 | 0 |\n")
                .contains("## entries the loaded rows do not bear out\n\nnone\n");
    }

    @Test
    void failsOnAnEntryItsRowDoesNotBearOut() throws IOException {
        Files.writeString(inputs.resolve(NcertErrataYamlReader.FILE), ENTRY);
        imports.alignmentByBook = Map.of("bio11", List.of(hindi(19, "19.2.8", 2, "इसके अतिरिक्त पेट …", 9)));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(out.toString())
                .contains("- bio11 hi ch 19 §19.2.8 ¶2 p8: the row is read from page(s) [9], not page 8\n")
                .contains("- bio11 hi ch 19 §19.2.8 ¶2 p8: the row does not carry \"ग्लूकागॉन\" (corrected)\n")
                .contains("- bio11 hi ch 19 §19.2.8 ¶2 p8: the row still carries the print \"पेट\" (corrected)\n")
                .contains("| bio11 | hi | 1 | 0 | 1 | 1 | 1 |\n")
                .contains("- result: FAILED: ncert-errata.yaml: 1 entry is not borne out by the loaded rows");
    }

    /**
     * A removal at the end of a row keeps only its left neighbour, so its corrected span sits inside its printed
     * one; a reload that lost the removal must still fail.
     */
    @Test
    void failsOnARemovalTheRowHasLost() throws IOException {
        Files.writeString(inputs.resolve(NcertErrataYamlReader.FILE), """
                errata:
                  - {book: bio11, lang: hi, chapter: 4, section: "4.2.3", para: 1, page: 6,
                     printed: "होता है, जिसमें लार्वा अवस्था नहीं होती", corrected: "होता है",
                     round: "bio11 Hindi round one, approved 2026-10-17 (ec40175)", meaning_changing: true,
                     action: corrected, flag_reviewed: false,
                     reason: "the page says indirect development has no larval stage"}
                """);
        // the approved entry's own span, as a reload that lost the removal would leave the row
        imports.alignmentByBook = Map.of("bio11", List.of(hindi(4, "4.2.3", 1,
                "अप्रत्यक्ष परिवर्धन होता है, जिसमें लार्वा अवस्था नहीं होती", 6)));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(out.toString())
                .doesNotContain("does not carry")
                .contains("- bio11 hi ch 4 §4.2.3 ¶1 p6: the row still carries the print \"होता है, जिसमें लार्वा "
                        + "अवस्था नहीं होती\" (corrected)\n");
    }

    @Test
    void namesAnEntryWhoseRowIsMissingOrCarriesNoTextInItsEdition() throws IOException {
        Files.writeString(inputs.resolve(NcertErrataYamlReader.FILE), ENTRY.replace("para: 2", "para: 3") + """
                  - {book: bio11, lang: en, chapter: 17, section: "17.2.2", para: 3, page: 6,
                     printed: "ADP and P_1", corrected: "ADP and P_i", round: "parked 2026-10-17",
                     meaning_changing: true, action: kept, flag_reviewed: false, reason: "P_1 for the phosphate P_i"}
                """);
        imports.alignmentByBook = Map.of("bio11", List.of(
                hindi(19, "19.2.8", 2, "इसके अतिरिक्त ग्लूकागॉन …", 8),
                hindi(17, "17.2.2", 3, "ADP और P_1 मुक्त कर …", 6)));

        assertThat(run()).isEqualTo(InputFileCommand.EXIT_FAILED);
        assertThat(out.toString())
                .contains("- bio11 hi ch 19 §19.2.8 ¶3 p8: no row at this address\n")
                .contains("- bio11 en ch 17 §17.2.2 ¶3 p6: the row carries no en text\n")
                .contains("| bio11 | en | 0 | 1 | 1 | 1 | 1 |\n");
    }

    private int run() {
        CommandLine commandLine = PipelineRunner.commandLine(
                        PipelineCommandTest.siblingFactory(imports, new Reports(ReportTest.CLOCK)))
                .setOut(new PrintWriter(out, true)).setErr(new PrintWriter(out, true));
        return commandLine.execute("ncert", "errata", "--inputs", inputs.toString(), "--reports", reports.toString());
    }

    /** A Hindi-only row, as a Hindi load leaves an address whose English another test does not need. */
    private static AlignmentRow hindi(int chapter, String section, int para, String text, int page) {
        return new AlignmentRow(UUID.randomUUID(), (short) chapter, section, (short) para, null, text, List.of(),
                List.of(page));
    }

    private static AlignmentRow english(int chapter, String section, int para, String text, int page) {
        return new AlignmentRow(UUID.randomUUID(), (short) chapter, section, (short) para, text, null, List.of(page),
                List.of());
    }

    private void assertRefused(String yaml, String message) throws IOException {
        Path file = write(yaml);
        assertThatThrownBy(() -> NcertErrataYamlReader.read(file))
                .isInstanceOf(InputFormatException.class)
                .hasMessageContaining(message);
    }

    private Path write(String yaml) throws IOException {
        Path file = inputs.resolve(NcertErrataYamlReader.FILE);
        Files.writeString(file, yaml);
        return file;
    }
}
