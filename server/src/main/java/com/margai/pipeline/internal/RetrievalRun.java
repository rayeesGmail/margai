package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.retrieval.HybridRetriever;
import com.margai.ai.retrieval.RetrievedPassages;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * The concept-query run that {@code ncert embed} ends with (TECH_PLAN §6.3, PLAN D15's second ✅):
 * every query in {@code eval/retrieval-queries.json} through {@link HybridRetriever}, its top
 * passages printed, and the whole set scored against the addresses each query was written to
 * reach.
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

        double reciprocalRank() {
            return hitRank == 0 ? 0.0 : 1.0 / hitRank;
        }

        boolean foundBy(String half) {
            return retrieved.passages().stream().anyMatch(passage -> passage.foundBy().equals(half)
                    || passage.foundBy().equals("both"));
        }
    }

    static List<Result> run(List<RetrievalQuery> queries, BookDefinition definition, HybridRetriever retriever,
            AiCallContext ctx) {
        List<Result> results = new ArrayList<>();
        for (RetrievalQuery query : queries) {
            RetrievedPassages retrieved = retriever.retrieve(query.text(), definition.row().subject(), null, ctx);
            results.add(new Result(query, retrieved, rankOfFirstExpected(query, retrieved)));
        }
        return results;
    }

    private static int rankOfFirstExpected(RetrievalQuery query, RetrievedPassages retrieved) {
        List<RetrievedPassages.Passage> passages = retrieved.passages();
        for (int index = 0; index < passages.size(); index++) {
            var paragraph = passages.get(index).paragraph();
            if (query.isExpected(paragraph.chapterNo(), paragraph.section(), paragraph.paraNo())) {
                return index + 1;
            }
        }
        return 0;
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
            List<RetrievedPassages.Passage> top = result.retrieved().passages().stream().limit(3).toList();
            if (top.isEmpty()) {
                report.line("  - nothing above the similarity floor");
                continue;
            }
            for (int index = 0; index < top.size(); index++) {
                RetrievedPassages.Passage passage = top.get(index);
                boolean expected = result.query().isExpected(passage.paragraph().chapterNo(),
                        passage.paragraph().section(), passage.paragraph().paraNo());
                report.line("  " + (index + 1) + ". " + (expected ? "✅ " : "") + passage.paragraph().address()
                        + " · " + passage.foundBy()
                        + (passage.similarity() == null ? "" : " · similarity %.3f".formatted(passage.similarity()))
                        + " · " + excerpt(passage.paragraph().text()));
            }
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
