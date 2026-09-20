package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.ai.retrieval.RetrievedPassages;
import com.margai.curriculum.api.ParagraphMatch;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * The scoring PLAN D15's second ✅ is read from, and that D17 re-runs over ~9,000 paragraphs and
 * compares against. Tested directly because an arithmetic slip here does not fail anything — it
 * just reports a different number, and the number is the whole deliverable.
 */
class RetrievalRunTest {

    @Test
    void theRankIsWhereTheFirstExpectedParagraphCameBack() {
        RetrievalRun.Result result = result(query("q1", "en", address(7, "7.9", 1)),
                passage(6, "6.1", 1), passage(7, "7.9", 1), passage(3, "3.2", 4));

        assertThat(result.hitRank()).isEqualTo(2);
        assertThat(result.hit(1)).isFalse();
        assertThat(result.hit(3)).isTrue();
        assertThat(result.reciprocalRank()).isEqualTo(0.5);
    }

    @Test
    void aParagraphThatNeverCameBackIsAMissAndScoresZero() {
        RetrievalRun.Result result = result(query("q1", "en", address(7, "7.9", 1)),
                passage(6, "6.1", 1), passage(3, "3.2", 4));

        assertThat(result.hitRank()).isZero();
        assertThat(result.hit(1)).isFalse();
        assertThat(result.hit(3)).isFalse();
        assertThat(result.reciprocalRank()).isZero();
    }

    /** Several addresses are right answers, so the score is the best of them, not the first listed. */
    @Test
    void withSeveralExpectedAddressesTheBestRankWins() {
        RetrievalQuery query = query("q1", "en", address(7, "7.8", 6), address(7, "7.8", 9));

        RetrievalRun.Result result = result(query, passage(7, "7.8", 9), passage(7, "7.8", 6));

        assertThat(result.hitRank()).isEqualTo(1);
    }

    /** An address that matches on two of three components is not a hit. */
    @Test
    void aNeighbouringParagraphIsNotTheExpectedOne() {
        RetrievalRun.Result result = result(query("q1", "en", address(7, "7.9", 1)),
                passage(7, "7.9", 2), passage(7, "7.10", 1), passage(6, "7.9", 1));

        assertThat(result.hitRank()).isZero();
    }

    /**
     * The reason Hindi is scored on its own line: folded into one average, five Hindi misses hide
     * inside ten English hits, which is the exact failure the cross-lingual pin was chosen to avoid.
     */
    @Test
    void hindiIsScoredSeparatelyFromEnglish() {
        List<RetrievalRun.Result> results = List.of(
                result(query("e1", "en", address(1, "1.1", 1)), passage(1, "1.1", 1)),
                result(query("e2", "en", address(2, "2.1", 1)), passage(2, "2.1", 1)),
                result(query("h1", "hi", address(3, "3.1", 1)), passage(9, "9.9", 9)),
                result(query("h2", "hi", address(4, "4.1", 1)), passage(9, "9.9", 9)));

        String rendered = render(results);

        assertThat(rendered).contains("| english | 2 | 2/2 (100%) | 2/2 (100%) | 1.000 |");
        assertThat(rendered).contains("| hindi → english paragraphs | 2 | 0/2 (0%) | 0/2 (0%) | 0.000 |");
        assertThat(rendered).as("and the blended number, which alone would read as 50%")
                .contains("| all | 4 | 2/4 (50%) | 2/4 (50%) | 0.500 |");
    }

    @Test
    void reportsWhichHalfOfTheHybridFired() {
        RetrievalQuery query = query("q1", "en", address(7, "7.9", 1));
        RetrievalRun.Result vectorOnly = new RetrievalRun.Result(query,
                new RetrievedPassages(List.of(new RetrievedPassages.Passage(
                        match(7, "7.9", 1), 0.5, 1, null, 0.8)), false, true), 1);
        RetrievalRun.Result textOnly = new RetrievalRun.Result(query,
                new RetrievedPassages(List.of(new RetrievedPassages.Passage(
                        match(7, "7.9", 1), 0.5, null, 1, null)), false, true), 1);

        String rendered = render(List.of(vectorOnly, textOnly));

        assertThat(rendered).contains("| vector | 1 of 2 |");
        assertThat(rendered).contains("| full text | 1 of 2 |");
    }

    @Test
    void namesAGroundingFailureRatherThanReportingAnEmptyTop3() {
        RetrievalRun.Result failed = new RetrievalRun.Result(query("q1", "en", address(7, "7.9", 1)),
                RetrievedPassages.groundingFailure(false), 0);

        String rendered = render(List.of(failed));

        assertThat(rendered).contains("grounding failure");
        assertThat(rendered).contains("nothing above the similarity floor");
    }

    @Test
    void marksTheExpectedParagraphInTheTop3Listing() {
        String rendered = render(List.of(result(query("q1", "en", address(7, "7.9", 1)),
                passage(6, "6.1", 1), passage(7, "7.9", 1))));

        assertThat(rendered).contains("2. ✅ phy11-part1 ch 7 §7.9 ¶1");
        assertThat(rendered).doesNotContain("1. ✅");
    }

    @Test
    void anEmptyQuerySetAddsNothingToTheReport() {
        Report report = new Report("t", InputReadersTest.INPUTS.resolve(NcertRegisterCommand.FILE));
        RetrievalRun.report(List.of(), List.of(), report);

        assertThat(report.render(ReportTest.CLOCK.nowIst())).doesNotContain("concept queries");
    }

    private static String render(List<RetrievalRun.Result> results) {
        Report report = new Report("t", InputReadersTest.INPUTS.resolve(NcertRegisterCommand.FILE));
        RetrievalRun.report(results, results.stream().map(RetrievalRun.Result::query).toList(), report);
        return report.render(ReportTest.CLOCK.nowIst());
    }

    private static RetrievalRun.Result result(RetrievalQuery query, ParagraphMatch... found) {
        List<RetrievedPassages.Passage> passages = new java.util.ArrayList<>();
        for (int index = 0; index < found.length; index++) {
            passages.add(new RetrievedPassages.Passage(found[index], 1.0 / (index + 1), index + 1, null, 0.9));
        }
        RetrievedPassages retrieved = new RetrievedPassages(passages, false, true);
        // The rank comes from the class under test, not from the test's own arithmetic.
        return new RetrievalRun.Result(query, retrieved, RetrievalRun.rankOfFirstExpected(query, retrieved));
    }

    private static RetrievalQuery query(String id, String language, RetrievalQuery.Address... expect) {
        return new RetrievalQuery(id, language, "why", List.of(expect), "n");
    }

    private static RetrievalQuery.Address address(int chapter, String section, int para) {
        return new RetrievalQuery.Address((short) chapter, section, (short) para);
    }

    private static ParagraphMatch passage(int chapter, String section, int para) {
        return match(chapter, section, para);
    }

    private static ParagraphMatch match(int chapter, String section, int para) {
        return new ParagraphMatch(UUID.randomUUID(), "phy11-part1", (short) chapter, section, (short) para,
                "Some text of the paragraph.", null, 0.9);
    }
}
