package com.margai.pipeline.internal;

import com.margai.ai.api.AiCallContext;
import com.margai.ai.retrieval.HybridRetriever;
import com.margai.ai.retrieval.RetrievedPassages;
import com.margai.curriculum.api.BookSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
                    groundingNote(result),
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
                .table(List.of("id", "subject", "rank filtered", "rank unfiltered", "grounded via, unfiltered"),
                        acceptance.stream()
                        .map(result -> List.of(result.query().id(), result.query().subject().name(),
                                rank(result), rankOf(unfiltered, result.query().id()),
                                groundedOf(unfiltered, result.query().id())))
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
        List<Result> heldOut = foundersHeldOut(filtered);
        if (!heldOut.isEmpty()) {
            report.section("held-out queries, unfused (D17's tick reads them fused: ncert retrieve --rewrite)")
                    .table(SCORE_HEADER, breakdown(heldOut));
        }
        List<Result> probes = gateProbes(filtered);
        if (!probes.isEmpty()) {
            report.section("the gate probes, unfused (the gate reads them fused: ncert retrieve --gate)")
                    .table(SCORE_HEADER, breakdown(probes));
        }

        report.section("which half of the hybrid actually fired (every filtered query)")
                .table(List.of("half", "queries it returned something for"), List.of(
                        List.of("vector", count(filtered, result -> result.foundBy("vector")) + " of " + filtered.size()),
                        List.of("full text", count(filtered, result -> result.foundBy("text")) + " of " + filtered.size())));

        report.section("per query (filtered)").table(
                List.of("id", "set", "subject", "lang", "rank of the expected paragraph", "note", "expected", "query"),
                filtered.stream().map(result -> List.of(result.query().id(), result.query().set().name(),
                        result.query().subject().name(), result.query().language(), rank(result),
                        groundingNote(result),
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

    // ── D18: the question fused with its English search version (DECISIONS 2026-10-19) ─────────────

    /* D17's held-out line: at least 3 of the founder's 4 held-out queries, "hit" read as in the answer set at any
       rank (the founder's ruling A, DECISIONS 2026-10-19). */
    static final int HELD_OUT_SIZE = 4;
    static final int HELD_OUT_IN_ANSWER_SET = 3;

    /**
     * Each query retrieved with its search version where it has one, fused (HybridRetriever); a query
     * without one is retrieved exactly as the unfused run retrieves it.
     */
    static List<Result> runFused(List<RetrievalQuery> queries, Map<String, String> searchVersions,
            HybridRetriever retriever, AiCallContext ctx) {
        List<Result> results = new ArrayList<>();
        for (RetrievalQuery query : queries) {
            RetrievedPassages retrieved = retriever.retrieve(query.text(), searchVersions.get(query.id()),
                    query.subject(), null, ctx);
            results.add(new Result(query, retrieved, rankOfFirstExpected(query, retrieved)));
        }
        return results;
    }

    /**
     * The held-out queries D17's line counts: the founder's four, not the Week-3 gate probes the held-out
     * set also carries (DECISIONS 2026-10-20).
     */
    static List<Result> foundersHeldOut(List<Result> results) {
        return inSet(results, RetrievalQuery.QuerySet.heldout).stream()
                .filter(result -> result.query().gate() == null).toList();
    }

    /** The held-out queries that probe a gate rather than D17's tick (DECISIONS 2026-10-20). */
    private static List<Result> gateProbes(List<Result> results) {
        return results.stream().filter(result -> result.query().gate() != null).toList();
    }

    /** The held-out line: a set that is not the founder's four cannot meet it, however it scores. */
    static Check heldOut(List<Result> heldOut) {
        long in = heldOut.stream().filter(Result::inAnswerSet).count();
        return new Check("held-out queries", "≥ " + HELD_OUT_IN_ANSWER_SET + " of " + HELD_OUT_SIZE,
                in + " of " + heldOut.size(), heldOut.size() == HELD_OUT_SIZE && in >= HELD_OUT_IN_ANSWER_SET);
    }

    /**
     * The rewrite run's report (D18). D17 ticks on the fused run: the bar on the acceptance set and at
     * least 3 of the 4 held-out queries (founder, DECISIONS 2026-10-19), with the unfused run beside it
     * for every set; the English acceptance queries rewritten too, scored apart from the bar; every
     * search version the model wrote; and the fused run's misses beside their expected text.
     *
     * @param unfused         every query retrieved on its own text, filtered to its subject
     * @param fused           the same queries, each non-English one retrieved with its search version
     * @param englishRewritten the English acceptance queries retrieved with their search versions
     * @param unfiltered      the acceptance set with no subject filter, unfused
     */
    static void reportFused(List<Result> unfused, List<Result> fused, List<Result> englishRewritten,
            List<Result> unfiltered, Map<String, String> searchVersions, Report report,
            Function<RetrievalQuery.Address, String> expectedText) {
        List<Check> tick = new ArrayList<>(bar(inSet(fused, RetrievalQuery.QuerySet.acceptance)));
        tick.add(heldOut(foundersHeldOut(fused)));
        boolean met = tick.stream().allMatch(Check::met);
        report.section("D17's tick, fused (founder, DECISIONS 2026-10-19) — " + (met ? "MET" : "NOT MET"))
                .line("the bar on the acceptance set and the held-out line, each non-English query retrieved with "
                        + "its English search version and fused, never the rewrite alone; every miss is diagnosed "
                        + "beside this report before the ✅ is judged")
                .table(List.of("check", "needed", "got", "met"), rows(tick));
        List<Check> unfusedTick = new ArrayList<>(bar(inSet(unfused, RetrievalQuery.QuerySet.acceptance)));
        unfusedTick.add(heldOut(foundersHeldOut(unfused)));
        report.section("the same checks, unfused (for comparison)")
                .table(List.of("check", "needed", "got", "met"), rows(unfusedTick));

        for (RetrievalQuery.QuerySet set : RetrievalQuery.QuerySet.values()) {
            // The held-out set is the founder's four here; the gate probes it also carries get their own table.
            Function<List<Result>, List<Result>> of = set == RetrievalQuery.QuerySet.heldout
                    ? RetrievalRun::foundersHeldOut : results -> inSet(results, set);
            List<Result> setUnfused = of.apply(unfused);
            if (setUnfused.isEmpty()) {
                continue;
            }
            report.section(set + " — unfused against fused")
                    .table(List.of("mode", "set", "queries", "in the answer set", "hit@1", "hit@3", "MRR"),
                            Stream.concat(labelled("unfused", breakdown(setUnfused)),
                                    labelled("fused", breakdown(of.apply(fused)))).toList());
        }
        if (!gateProbes(unfused).isEmpty()) {
            report.section("the gate probes — unfused against fused (the gate reads them alone: ncert retrieve --gate)")
                    .table(List.of("mode", "set", "queries", "in the answer set", "hit@1", "hit@3", "MRR"),
                            Stream.concat(labelled("unfused", breakdown(gateProbes(unfused))),
                                    labelled("fused", breakdown(gateProbes(fused)))).toList());
        }
        if (!englishRewritten.isEmpty()) {
            List<String> ids = englishRewritten.stream().map(result -> result.query().id()).toList();
            report.section("the English acceptance queries rewritten too (founder's diagnostic, not part of the bar)")
                    .table(List.of("mode", "set", "queries", "in the answer set", "hit@1", "hit@3", "MRR"),
                            Stream.concat(labelled("unfused", breakdown(unfused.stream()
                                            .filter(result -> ids.contains(result.query().id())).toList())),
                                    labelled("rewritten and fused", breakdown(englishRewritten))).toList())
                    .table(List.of("id", "rank unfused", "rank rewritten and fused", "grounded via, unfused",
                                    "grounded via, rewritten and fused"), englishRewritten.stream()
                            .map(result -> List.of(result.query().id(), rankOf(unfused, result.query().id()),
                                    rank(result), groundedOf(unfused, result.query().id()),
                                    result.retrieved().groundedVia().name())).toList());
        }
        report.section("acceptance — no subject filter, unfused (diagnostic)")
                .table(SCORE_HEADER, breakdown(unfiltered))
                .table(List.of("id", "rank", "grounded via"), unfiltered.stream()
                        .map(result -> List.of(result.query().id(), rank(result),
                                result.retrieved().groundedVia().name())).toList());

        report.section("the search versions the model wrote")
                .table(List.of("id", "lang", "query", "search version"), searchVersions.entrySet().stream()
                        .map(entry -> {
                            RetrievalQuery query = queryOf(unfused, entry.getKey());
                            return List.of(entry.getKey(), query.language(), query.text(), entry.getValue());
                        }).toList());

        // Which text grounded each query, in each mode (founder ruling, DECISIONS 2026-10-19): fused, the
        // search version's vector alone can ground a question whose own stayed under the floor.
        report.section("per query").table(
                List.of("id", "set", "subject", "lang", "rank unfused", "rank fused", "grounded via, unfused",
                        "grounded via, fused", "expected"),
                fused.stream().map(result -> List.of(result.query().id(), result.query().set().name(),
                        result.query().subject().name(), result.query().language(),
                        rankOf(unfused, result.query().id()), rank(result),
                        groundedOf(unfused, result.query().id()), result.retrieved().groundedVia().name(),
                        result.query().expect().toString())).toList());

        // Every fused retrieval: the bar's, and the English acceptance queries rewritten for the diagnostic.
        handCheck(Stream.concat(fused.stream(), englishRewritten.stream()).toList(), searchVersions, report);
        misses(fused, searchVersions, report, expectedText, false);

        report.section("top 3 passages per query, fused");
        for (Result result : fused) {
            report.line("");
            report.line("**" + result.query().id() + "** (" + result.query().language() + ") — " + result.query().text());
            topThree(result, report);
        }
    }

    /**
     * The Week-3 gate probes' report (D18; the founder's ruling, DECISIONS 2026-10-20): the gate is read
     * book by book, each probe covering one book-level gap in D17's run of record, so each probe is listed
     * with the books its key is in; a probe in the answer set closes its gap, and a miss stops for the
     * founder's ruling. Read fused — a non-English probe with its English search version, never the rewrite
     * alone; an English probe on its own text — with the unfused rank beside it. A miss lists its whole fused
     * result, not the top three, so the paragraphs that ranked above the key are on the page.
     */
    static void reportGate(String gate, List<Result> unfused, List<Result> fused, Map<String, String> searchVersions,
            Report report, Function<RetrievalQuery.Address, String> expectedText) {
        long in = count(fused, Result::inAnswerSet);
        report.section("the Week-3 gate probes (gate " + gate + ") — " + in + " of " + fused.size()
                        + " in the answer set" + (in == fused.size() ? "" : "; a miss stops for the founder's ruling"))
                .line("each probe read fused — a non-English probe retrieved with its English search version and "
                        + "fused, never the rewrite alone; an English probe on its own text — the unfused rank beside it")
                .table(List.of("id", "books", "subject", "lang", "rank unfused", "rank fused", "grounded via, unfused",
                                "grounded via, fused", "in the answer set"),
                        fused.stream().map(result -> List.of(result.query().id(), books(result.query()),
                                result.query().subject().name(), result.query().language(),
                                rankOf(unfused, result.query().id()), rank(result),
                                groundedOf(unfused, result.query().id()), result.retrieved().groundedVia().name(),
                                result.inAnswerSet() ? "yes" : "NO")).toList());
        report.section("the search versions the model wrote")
                .table(List.of("id", "lang", "query", "search version"), searchVersions.entrySet().stream()
                        .map(entry -> {
                            RetrievalQuery query = queryOf(unfused, entry.getKey());
                            return List.of(entry.getKey(), query.language(), query.text(), entry.getValue());
                        }).toList());
        handCheck(fused, searchVersions, report);
        misses(fused, searchVersions, report, expectedText, true);
        report.section("top 3 passages per probe, fused");
        for (Result result : fused) {
            report.line("");
            report.line("**" + result.query().id() + "** (" + result.query().language() + ") — " + result.query().text());
            topThree(result, report);
        }
    }

    /**
     * The founder's hand-check (DECISIONS 2026-10-19): for every retrieval only its search version grounded,
     * does the top passage answer the original question? Printed whole, for the verdict to be read from.
     */
    private static void handCheck(List<Result> fusedRetrievals, Map<String, String> searchVersions, Report report) {
        List<Result> viaSearchVersion = fusedRetrievals.stream()
                .filter(result -> result.retrieved().groundedVia() == RetrievedPassages.GroundedVia.search_version)
                .toList();
        report.section("grounded only through the search version — the top passage in full, for the hand-check ("
                + viaSearchVersion.size() + ")");
        if (viaSearchVersion.isEmpty()) {
            report.line("none");
        }
        for (Result result : viaSearchVersion) {
            report.line("");
            report.line("**" + result.query().id() + "** (" + result.query().set() + ", " + result.query().language()
                    + ") — " + result.query().text());
            report.line("  search version: " + searchVersions.get(result.query().id()));
            List<RetrievedPassages.Passage> passages = result.retrieved().passages();
            if (passages.isEmpty()) {
                report.line("  top passage: none — grounded, but no passage fits under the token cap");
                continue;
            }
            RetrievedPassages.Passage top = passages.getFirst();
            String text = top.paragraph().text() == null ? "" : top.paragraph().text().replaceAll("\\s+", " ").strip();
            report.line("  top passage: " + top.paragraph().address() + (isExpected(result.query(), top) ? " ✅" : "")
                    + " · " + text);
        }
    }

    /** Each fused miss beside the text it was expected to reach; with {@code everyPassage}, all it returned. */
    private static void misses(List<Result> fused, Map<String, String> searchVersions, Report report,
            Function<RetrievalQuery.Address, String> expectedText, boolean everyPassage) {
        List<Result> misses = fused.stream().filter(result -> !result.inAnswerSet()).toList();
        report.section("misses, fused — what was expected, and what came back (" + misses.size() + ")");
        if (misses.isEmpty()) {
            report.line("none");
        }
        for (Result miss : misses) {
            report.line("");
            report.line("**" + miss.query().id() + "** (" + miss.query().set() + ", " + miss.query().subject() + ", "
                    + miss.query().language() + ") — " + miss.query().text());
            String version = searchVersions.get(miss.query().id());
            if (version != null) {
                report.line("  search version: " + version);
            }
            for (RetrievalQuery.Address address : miss.query().expect()) {
                String text = expectedText.apply(address);
                report.line("  - expected " + address + " · " + (text == null ? "NO ROW AT THIS ADDRESS" : excerpt(text)));
            }
            report.line(everyPassage ? "  came back, every passage:" : "  came back:");
            if (everyPassage) {
                passages(miss, miss.retrieved().passages().size(), report);
            } else {
                topThree(miss, report);
            }
        }
    }

    /** The books a query's key is in, for the gate's book-by-book reading. */
    private static String books(RetrievalQuery query) {
        return query.expect().stream().map(RetrievalQuery.Address::book).distinct()
                .collect(java.util.stream.Collectors.joining(", "));
    }

    private static List<List<String>> rows(List<Check> checks) {
        return checks.stream()
                .map(check -> List.of(check.check(), check.needed(), check.got(), check.met() ? "yes" : "no"))
                .toList();
    }

    private static Stream<List<String>> labelled(String mode, List<List<String>> rows) {
        return rows.stream().map(row -> Stream.concat(Stream.of(mode), row.stream()).toList());
    }

    private static String rankOf(List<Result> results, String id) {
        return results.stream().filter(result -> result.query().id().equals(id)).findFirst()
                .map(RetrievalRun::rank).orElse("not run");
    }

    /** Every retrieval names what grounded it (DECISIONS 2026-10-19), or says plainly that nothing did. */
    private static String groundingNote(Result result) {
        return result.retrieved().grounded() ? "grounded via " + result.retrieved().groundedVia() : "grounding failure";
    }

    private static String groundedOf(List<Result> results, String id) {
        return results.stream().filter(result -> result.query().id().equals(id)).findFirst()
                .map(result -> result.retrieved().groundedVia().name()).orElse("not run");
    }

    private static RetrievalQuery queryOf(List<Result> results, String id) {
        return results.stream().map(Result::query).filter(query -> query.id().equals(id)).findFirst().orElseThrow();
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
        passages(result, 3, report);
    }

    /** The first {@code limit} passages a retrieval returned, in rank order, the expected ones marked. */
    private static void passages(Result result, int limit, Report report) {
        List<RetrievedPassages.Passage> top = result.retrieved().passages().stream().limit(limit).toList();
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
