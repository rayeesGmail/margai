package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.retrieval.HybridRetriever;
import com.margai.ai.retrieval.RetrievedPassages;
import com.margai.curriculum.api.BookSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The concept-query run that {@code ncert embed} ends with for its own book (TECH_PLAN §6.3, PLAN
 * D15's second ✅) and that {@code ncert retrieve} runs over the whole corpus (PLAN D17): queries
 * from {@code eval/retrieval-queries.json} through {@link HybridRetriever}, their top passages
 * printed, and each set scored against the addresses its queries were written to reach.
 *
 * <p>It is scored rather than only printed because a printed list of fifteen passage sets is a
 * thing to read once and an unscored run proves nothing twice: D17 re-runs this over ~9,000
 * paragraphs and needs a number from today to compare against.
 *
 * <p>Hindi is scored separately and on purpose. A Hindi query is expected to reach an
 * <em>English</em> paragraph through the pinned model's cross-lingual space (§6.4), and that is
 * the single most expensive thing to have wrong: folded into one average, five Hindi misses would
 * hide inside ten English hits, which is exactly the failure the pin was chosen to avoid.
 */
final class RetrievalRun {

    private RetrievalRun() {
    }

    /**
     * @param hitRank 1-based rank of the first expected paragraph, or 0 when none came back —
     *                so 1 is "the top passage was right" and 0 is a miss
     */
    record Result(RetrievalQuery query, RetrievedPassages retrieved, int hitRank) {

        boolean hit(int within) {
            return hitRank >= 1 && hitRank <= within;
        }

        /** Any rank: what comes back is already the token-capped set the answer step reads. */
        boolean inAnswerSet() {
            return hitRank >= 1;
        }

        double reciprocalRank() {
            return hitRank == 0 ? 0.0 : 1.0 / hitRank;
        }

        boolean foundBy(String half) {
            return retrieved.passages().stream().anyMatch(passage -> passage.foundBy().equals(half)
                    || passage.foundBy().equals("both"));
        }
    }

    /** Each query filtered to its own subject, as the doubt pipeline filters (§4.3 stage 6). */
    static List<Result> run(List<RetrievalQuery> queries, HybridRetriever retriever, AiCallContext ctx) {
        List<Result> results = new ArrayList<>();
        for (RetrievalQuery query : queries) {
            RetrievedPassages retrieved = retriever.retrieve(query.text(), query.subject(), null, ctx);
            results.add(new Result(query, retrieved, rankOfFirstExpected(query, retrieved)));
        }
        return results;
    }

    static int rankOfFirstExpected(RetrievalQuery query, RetrievedPassages retrieved) {
        List<RetrievedPassages.Passage> passages = retrieved.passages();
        for (int index = 0; index < passages.size(); index++) {
            if (isExpected(query, passages.get(index))) {
                return index + 1;
            }
        }
        return 0;
    }

    private static boolean isExpected(RetrievalQuery query, RetrievedPassages.Passage passage) {
        var paragraph = passage.paragraph();
        return query.isExpected(paragraph.bookCode(), paragraph.chapterNo(), paragraph.section(), paragraph.paraNo());
    }

    static void report(List<Result> results, List<RetrievalQuery> queries, Report report) {
        if (queries.isEmpty()) {
            return;
        }
        if (results.isEmpty()) {
            report.section("concept queries").line("not run");
            return;
        }

        report.section("concept queries — the score (PLAN D15 ✅)")
                .table(List.of("set", "queries", "hit@1", "hit@3", "MRR"), List.of(
                        score("all", results, result -> true),
                        score("english", results, result -> !result.query().isHindi()),
                        score("hindi → english paragraphs", results, result -> result.query().isHindi())));

        report.section("which half of the hybrid actually fired")
                .line("a query the text half never answers is a query where hybrid retrieval is vector retrieval")
                .line("counted over the passages that survived the token cap, and a query whose grounding failed "
                        + "carries none — so this under-reports both halves rather than over-reporting either")
                .table(List.of("half", "queries it returned something for"), List.of(
                        List.of("vector", count(results, result -> result.foundBy("vector")) + " of " + results.size()),
                        List.of("full text", count(results, result -> result.foundBy("text")) + " of " + results.size())));

        List<List<String>> rows = new ArrayList<>();
        for (Result result : results) {
            rows.add(List.of(result.query().id(), result.query().language(),
                    result.hitRank() == 0 ? "miss" : String.valueOf(result.hitRank()),
                    result.retrieved().grounded() ? "" : "grounding failure",
                    result.query().expect().toString(),
                    result.query().text()));
        }
        report.section("per query")
                .table(List.of("id", "lang", "rank of the expected paragraph", "note", "expected", "query"), rows);

        report.section("top 3 passages per query");
        for (Result result : results) {
            report.line("");
            report.line("**" + result.query().id() + "** (" + result.query().language() + ") — "
                    + result.query().text());
            topThree(result, report);
        }
    }

    // ── D17: the whole corpus (ncert retrieve) ──────────────────────────────────────────────────────

    /*
     * The founder's bar for D17's ✅ (DECISIONS 2026-10-18), fixed before the run so the run cannot
     * grade itself: at least 13 of the 15 acceptance queries with an expected paragraph in the answer
     * set, every Hindi query among them, and no subject below 4 of its 5.
     */
    static final int ACCEPTANCE_SIZE = 15;
    static final int ACCEPTANCE_IN_ANSWER_SET = 13;
    static final int PER_SUBJECT = 5;
    static final int PER_SUBJECT_IN_ANSWER_SET = 4;

    /** One line of the bar: what it asks, what the run got, and whether that meets it. */
    record Check(String check, String needed, String got, boolean met) {
    }

    /**
     * The same queries with no subject filter (the founder's diagnostic, DECISIONS 2026-10-18): what
     * a router that guessed no subject would cost, read against the filtered run query by query.
     */
    static List<Result> runUnfiltered(List<RetrievalQuery> queries, HybridRetriever retriever, AiCallContext ctx) {
        List<Result> results = new ArrayList<>();
        for (RetrievalQuery query : queries) {
            RetrievedPassages retrieved = retriever.retrieve(query.text(), null, null, ctx);
            results.add(new Result(query, retrieved, rankOfFirstExpected(query, retrieved)));
        }
        return results;
    }

    /**
     * The bar, read from the subject-filtered acceptance results. "In the answer set" is a hit at any
     * rank: the passages a run returns are already the ones under the token cap, which is what the
     * answer step reads (§4.3 stages 6–7). A set that is not 15 queries, five per subject, cannot meet
     * the bar however it scores, because the bar is written for that set.
     */
    static List<Check> bar(List<Result> acceptance) {
        List<Check> checks = new ArrayList<>();
        long inAnswerSet = acceptance.stream().filter(Result::inAnswerSet).count();
        checks.add(new Check("in the answer set", "≥ " + ACCEPTANCE_IN_ANSWER_SET + " of " + ACCEPTANCE_SIZE,
                inAnswerSet + " of " + acceptance.size(),
                acceptance.size() == ACCEPTANCE_SIZE && inAnswerSet >= ACCEPTANCE_IN_ANSWER_SET));
        List<Result> hindi = acceptance.stream().filter(result -> result.query().isHindi()).toList();
        long hindiIn = hindi.stream().filter(Result::inAnswerSet).count();
        checks.add(new Check("every Hindi query", "all", hindiIn + " of " + hindi.size(),
                !hindi.isEmpty() && hindiIn == hindi.size()));
        for (BookSubject subject : BookSubject.values()) {
            List<Result> ofSubject = acceptance.stream().filter(result -> result.query().subject() == subject).toList();
            long in = ofSubject.stream().filter(Result::inAnswerSet).count();
            checks.add(new Check(subject.name(), "≥ " + PER_SUBJECT_IN_ANSWER_SET + " of " + PER_SUBJECT,
                    in + " of " + ofSubject.size(),
                    ofSubject.size() == PER_SUBJECT && in >= PER_SUBJECT_IN_ANSWER_SET));
        }
        return checks;
    }

    /**
     * The corpus-wide report: the bar first, then each set scored on its own — acceptance filtered
     * (the ✅) and unfiltered (the diagnostic), the D15 baseline, and any diagnostic queries — and
     * every miss with the text it was expected to reach beside what came back, which is what the
     * miss's diagnosis (answer key or retrieval) is written from.
     *
     * @param expectedText an expected address's English text, or null when no row is there
     */
    static void reportCorpus(List<Result> filtered, List<Result> unfiltered, Report report,
            Function<RetrievalQuery.Address, String> expectedText) {
        List<Result> acceptance = inSet(filtered, RetrievalQuery.QuerySet.acceptance);
        List<Check> checks = bar(acceptance);
        boolean met = checks.stream().allMatch(Check::met);
        report.section("the D17 bar (founder, DECISIONS 2026-10-18) — " + (met ? "MET" : "NOT MET"))
                .line("read from the acceptance set, each query filtered to its subject; every miss is diagnosed "
                        + "(answer key or retrieval) beside this report before the ✅ is judged")
                .table(List.of("check", "needed", "got", "met"), checks.stream()
                        .map(check -> List.of(check.check(), check.needed(), check.got(), check.met() ? "yes" : "no"))
                        .toList());

        report.section("acceptance — each query filtered to its subject (the ✅)")
                .table(SCORE_HEADER, breakdown(acceptance));
        report.section("acceptance — no subject filter (diagnostic: what a router's missing subject would cost)")
                .table(SCORE_HEADER, breakdown(unfiltered))
                .table(List.of("id", "subject", "rank filtered", "rank unfiltered"), acceptance.stream()
                        .map(result -> List.of(result.query().id(), result.query().subject().name(),
                                rank(result), unfiltered.stream()
                                        .filter(other -> other.query().id().equals(result.query().id()))
                                        .findFirst().map(RetrievalRun::rank).orElse("not run")))
                        .toList());

        List<Result> baseline = inSet(filtered, RetrievalQuery.QuerySet.baseline);
        if (!baseline.isEmpty()) {
            report.section("baseline — D15's fifteen over the whole corpus")
                    .line("D15 on the ten-book corpus, filtered to physics: hit@1 7/15, hit@3 10/15, MRR 0.593 "
                            + "(english 0.639, hindi 0.500), scored then on chapter, section and paragraph without the "
                            + "book (TRACKER day log, D15); here the book is part of the address")
                    .table(SCORE_HEADER, breakdown(baseline));
        }
        List<Result> diagnostic = inSet(filtered, RetrievalQuery.QuerySet.diagnostic);
        if (!diagnostic.isEmpty()) {
            report.section("diagnostic queries — scored, deciding nothing")
                    .table(SCORE_HEADER, breakdown(diagnostic));
        }

        report.section("which half of the hybrid actually fired (every filtered query)")
                .table(List.of("half", "queries it returned something for"), List.of(
                        List.of("vector", count(filtered, result -> result.foundBy("vector")) + " of " + filtered.size()),
                        List.of("full text", count(filtered, result -> result.foundBy("text")) + " of " + filtered.size())));

        report.section("per query (filtered)").table(
                List.of("id", "set", "subject", "lang", "rank of the expected paragraph", "note", "expected", "query"),
                filtered.stream().map(result -> List.of(result.query().id(), result.query().set().name(),
                        result.query().subject().name(), result.query().language(), rank(result),
                        result.retrieved().grounded() ? "" : "grounding failure",
                        result.query().expect().toString(), result.query().text())).toList());

        List<Result> misses = filtered.stream().filter(result -> !result.inAnswerSet()).toList();
        report.section("misses — what was expected, and what came back (" + misses.size() + ")");
        if (misses.isEmpty()) {
            report.line("none");
        }
        for (Result miss : misses) {
            report.line("");
            report.line("**" + miss.query().id() + "** (" + miss.query().set() + ", " + miss.query().subject() + ", "
                    + miss.query().language() + ") — " + miss.query().text());
            for (RetrievalQuery.Address address : miss.query().expect()) {
                String text = expectedText.apply(address);
                report.line("  - expected " + address + " · " + (text == null ? "NO ROW AT THIS ADDRESS" : excerpt(text)));
            }
            report.line("  came back:");
            topThree(miss, report);
        }

        report.section("top 3 passages per query (filtered)");
        for (Result result : filtered) {
            report.line("");
            report.line("**" + result.query().id() + "** (" + result.query().language() + ") — " + result.query().text());
            topThree(result, report);
        }
    }

    private static final List<String> SCORE_HEADER =
            List.of("set", "queries", "in the answer set", "hit@1", "hit@3", "MRR");

    /** All, then by language, then by subject — each a line of its own, so no average hides a part. */
    private static List<List<String>> breakdown(List<Result> results) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(corpusScore("all", results));
        for (String language : List.of("en", "hi", "hinglish")) {
            List<Result> ofLanguage = results.stream().filter(result -> result.query().language().equals(language)).toList();
            if (!ofLanguage.isEmpty()) {
                rows.add(corpusScore(language, ofLanguage));
            }
        }
        for (BookSubject subject : BookSubject.values()) {
            List<Result> ofSubject = results.stream().filter(result -> result.query().subject() == subject).toList();
            if (!ofSubject.isEmpty()) {
                rows.add(corpusScore(subject.name(), ofSubject));
            }
        }
        return rows;
    }

    private static List<String> corpusScore(String label, List<Result> set) {
        double mrr = set.stream().mapToDouble(Result::reciprocalRank).average().orElse(0);
        return List.of(label, String.valueOf(set.size()), fraction(set, Result::inAnswerSet),
                fraction(set, result -> result.hit(1)), fraction(set, result -> result.hit(3)), "%.3f".formatted(mrr));
    }

    private static List<Result> inSet(List<Result> results, RetrievalQuery.QuerySet set) {
        return results.stream().filter(result -> result.query().set() == set).toList();
    }

    private static String rank(Result result) {
        return result.hitRank() == 0 ? "miss" : String.valueOf(result.hitRank());
    }

    /** A query's first three passages, the expected one marked; or why there are none. */
    private static void topThree(Result result, Report report) {
        List<RetrievedPassages.Passage> top = result.retrieved().passages().stream().limit(3).toList();
        if (top.isEmpty()) {
            report.line("  - nothing above the similarity floor");
            return;
        }
        for (int index = 0; index < top.size(); index++) {
            RetrievedPassages.Passage passage = top.get(index);
            report.line("  " + (index + 1) + ". " + (isExpected(result.query(), passage) ? "✅ " : "")
                    + passage.paragraph().address() + " · " + passage.foundBy()
                    + (passage.similarity() == null ? "" : " · similarity %.3f".formatted(passage.similarity()))
                    + " · " + excerpt(passage.paragraph().text()));
        }
    }

    private static List<String> score(String label, List<Result> results, Predicate<Result> included) {
        List<Result> set = results.stream().filter(included).toList();
        if (set.isEmpty()) {
            return List.of(label, "0", "—", "—", "—");
        }
        double mrr = set.stream().mapToDouble(Result::reciprocalRank).average().orElse(0);
        return List.of(label, String.valueOf(set.size()),
                fraction(set, result -> result.hit(1)), fraction(set, result -> result.hit(3)),
                "%.3f".formatted(mrr));
    }

    private static String fraction(List<Result> set, Predicate<Result> hit) {
        long hits = set.stream().filter(hit).count();
        return hits + "/" + set.size() + " (%.0f%%)".formatted(100.0 * hits / set.size());
    }

    private static long count(List<Result> results, Predicate<Result> matching) {
        return results.stream().filter(matching).count();
    }

    private static String excerpt(String text) {
        if (text == null) {
            return "";
        }
        String flat = text.replaceAll("\\s+", " ").strip();
        return flat.length() <= 90 ? flat : flat.substring(0, 89) + "…";
    }
}
