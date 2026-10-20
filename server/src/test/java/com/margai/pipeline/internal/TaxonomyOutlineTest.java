package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.tasks.PyqTag;
import com.margai.ai.tasks.QuestionTagger;
import com.margai.curriculum.api.PaperSubject;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The taxonomy as {@code pyq tag} hands it to the model and checks what comes back (D19), over the
 * committed {@code taxonomy.csv}: a subject's outline carries its own units, chapters and topics as
 * data, in the founder's order, and nothing of another subject; any biology section — Biology, Botany
 * or Zoology — carries botany and zoology together; and a tag is refused in code for every way it
 * can be wrong about the list.
 */
class TaxonomyOutlineTest {

    private static final TaxonomyOutline TAXONOMY = new TaxonomyOutline(
            TaxonomyCsvReader.read(Path.of(InputsMixin.DEFAULT_INPUTS).resolve(TaxonomyLoadCommand.FILE)));

    @Test
    void aSubjectsOutlineCarriesItsOwnCodesInTheFoundersOrder() {
        List<QuestionTagger.OutlineLine> physics = TAXONOMY.outline(PaperSubject.physics);
        List<String> codes = physics.stream().map(QuestionTagger.OutlineLine::code).toList();

        assertThat(physics.getFirst()).isEqualTo(new QuestionTagger.OutlineLine(0, "subject", null, "Physics"));
        assertThat(physics).contains(new QuestionTagger.OutlineLine(2, "chapter", "PHY.11.ROT",
                "Systems of Particles and Rotational Motion"));
        assertThat(physics).anySatisfy(line -> assertThat(line).extracting(QuestionTagger.OutlineLine::depth,
                QuestionTagger.OutlineLine::kind, QuestionTagger.OutlineLine::code).containsExactly(3, "topic", "PHY.11.ROT.MOI"));
        assertThat(codes).doesNotContain("CHE.11.BASICS");
        assertThat(codes.indexOf("PHY.11.ROT.COM")).isLessThan(codes.indexOf("PHY.11.ROT.MOI"));
    }

    @Test
    void anyBiologySectionsOutlineIsBotanyAndZoology() {
        for (PaperSubject printed : List.of(PaperSubject.biology, PaperSubject.botany, PaperSubject.zoology)) {
            assertThat(TAXONOMY.outline(printed)).filteredOn(line -> line.kind().equals("subject"))
                    .extracting(QuestionTagger.OutlineLine::name).containsExactly("Botany", "Zoology");
        }
    }

    @Test
    void aTagIsCheckedAgainstTheListInCode() {
        assertThat(TAXONOMY.problem(PaperSubject.physics, tag("PHY.11.ROT.MOI", List.of("PHY.11.ROT.COM"), false))).isNull();
        assertThat(TAXONOMY.problem(PaperSubject.physics, tag("PHY.11.NOPE", List.of(), false)))
                .contains("is not a code of the taxonomy");
        assertThat(TAXONOMY.problem(PaperSubject.physics, tag("PHY.U05", List.of(), false))).contains("is a unit");
        assertThat(TAXONOMY.problem(PaperSubject.physics, tag("CHE.11.BASICS.MOLE", List.of(), false)))
                .contains("is chemistry, but the question is printed under physics");
        assertThat(TAXONOMY.problem(PaperSubject.physics, tag("PHY.11.ROT.MOI", List.of(), true)))
                .contains("is not a chapter — it keeps the nearest chapter");
        assertThat(TAXONOMY.problem(PaperSubject.physics, tag("PHY.11.ROT", List.of(), true))).isNull();
        assertThat(TAXONOMY.problem(PaperSubject.physics, tag("PHY.11.ROT.MOI", List.of("PHY.11.ROT.MOI"), false)))
                .contains("repeats the primary");
    }

    /** A Zoology section's biotechnology question goes where the taxonomy files biotechnology: under botany. */
    @Test
    void aZoologySectionsQuestionMayBeTaggedOnABotanyChapter() {
        String botanyChapter = TAXONOMY.outline(PaperSubject.botany).stream()
                .filter(line -> line.kind().equals("chapter") && line.code().startsWith("BOT.")).findFirst().orElseThrow().code();

        assertThat(TAXONOMY.problem(PaperSubject.zoology, tag(botanyChapter, List.of(), false))).isNull();
        assertThat(TAXONOMY.problem(PaperSubject.chemistry, tag(botanyChapter, List.of(), false)))
                .contains("but the question is printed under chemistry");
        assertThat(TAXONOMY.chapterOf("PHY.11.ROT.MOI")).startsWith("PHY.11.ROT ");
    }

    private static PyqTag tag(String primary, List<String> secondary, boolean outOfSyllabus) {
        return new PyqTag(primary, secondary, outOfSyllabus, new BigDecimal("0.9"), "a reason");
    }
}
