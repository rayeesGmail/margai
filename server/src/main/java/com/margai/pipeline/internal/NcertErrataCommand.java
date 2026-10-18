package com.margai.pipeline.internal;

import com.margai.curriculum.api.AlignmentRow;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImport;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * {@code ncert errata} (D16, DECISIONS 2026-10-17): {@code ncert-errata.yaml} checked against the rows as they are
 * loaded. Each entry names its row by address, so a reload that moves or rewrites the row would leave the log
 * untrue without a word; this reads every book the file names and fails the run on an entry whose row is not
 * there, is not on the entry's page, or does not carry the entry's span — its correction, or the print it keeps —
 * and on a correction whose row still carries the print, which is what a reload that lost a removal leaves (the
 * corrected span of a removal sits inside the printed one). It costs nothing and changes nothing.
 */
@Component
@Profile("pipeline")
@Command(name = "errata", mixinStandardHelpOptions = true,
        description = "Check ncert-errata.yaml against the loaded rows: each entry's row, page and span; changes nothing.")
class NcertErrataCommand extends InputFileCommand {

    private final CurriculumImport imports;

    NcertErrataCommand(CurriculumImport imports, Reports reports) {
        super(reports);
        this.imports = imports;
    }

    @Override
    String inputFileName() {
        return NcertErrataYamlReader.FILE;
    }

    @Override
    void run(Path inputFile, Report report) {
        List<NcertErratum> errata = NcertErrataYamlReader.read(inputFile);
        Map<String, List<NcertErratum>> byBook = errata.stream()
                .collect(Collectors.groupingBy(NcertErratum::book, LinkedHashMap::new, Collectors.toList()));
        report.read(errata.size() + " errata over " + byBook.size() + " book(s)");

        List<List<String>> tally = new ArrayList<>();
        List<String> problems = new ArrayList<>();
        int unborne = 0;
        for (Map.Entry<String, List<NcertErratum>> book : byBook.entrySet()) {
            TreeSet<Short> chapters = book.getValue().stream().map(NcertErratum::chapter)
                    .collect(Collectors.toCollection(TreeSet::new));
            Map<String, AlignmentRow> rows = imports.alignmentRows(book.getKey(), chapters).stream()
                    .collect(Collectors.toMap(AlignmentRow::address, Function.identity()));
            for (BookLanguage language : BookLanguage.values()) {
                List<NcertErratum> edition = book.getValue().stream()
                        .filter(erratum -> erratum.language() == language).toList();
                if (edition.isEmpty()) {
                    continue;
                }
                int failing = 0;
                for (NcertErratum erratum : edition) {
                    int before = problems.size();
                    check(erratum, rows.get(erratum.address()), problems);
                    failing += problems.size() > before ? 1 : 0;
                }
                unborne += failing;
                tally.add(List.of(book.getKey(), language.name(),
                        count(edition, erratum -> erratum.action() == NcertErratum.Action.corrected),
                        count(edition, erratum -> erratum.action() == NcertErratum.Action.kept),
                        count(edition, NcertErratum::meaningChanging),
                        count(edition, erratum -> !erratum.flagReviewed()),
                        String.valueOf(failing)));
            }
        }
        report.section("errata by book")
                .table(List.of("book", "lang", "corrected", "kept", "meaning_changing", "flag not reviewed",
                        "not borne out"), tally);
        report.section("entries the loaded rows do not bear out").list(problems);
        if (unborne > 0) {
            throw new InputFormatException(inputFile, 0, unborne
                    + " entr" + (unborne == 1 ? "y is" : "ies are") + " not borne out by the loaded rows — listed above");
        }
    }

    private static void check(NcertErratum erratum, AlignmentRow row, List<String> problems) {
        String where = erratum.book() + " " + erratum.language() + " " + erratum.address() + " p" + erratum.page();
        if (row == null) {
            problems.add(where + ": no row at this address");
            return;
        }
        boolean english = erratum.language() == BookLanguage.en;
        String text = english ? row.textEn() : row.textHi();
        if (text == null) {
            problems.add(where + ": the row carries no " + erratum.language() + " text");
            return;
        }
        List<Integer> pages = english ? row.pagesEn() : row.pagesHi();
        if (!pages.contains(erratum.page())) {
            problems.add(where + ": the row is read from page(s) " + pages + ", not page " + erratum.page());
        }
        if (!text.contains(erratum.carried())) {
            problems.add(where + ": the row does not carry \"" + erratum.carried() + "\" ("
                    + erratum.action() + ")");
        }
        if (erratum.action() == NcertErratum.Action.corrected && text.contains(erratum.printed())) {
            problems.add(where + ": the row still carries the print \"" + erratum.printed() + "\" (corrected)");
        }
    }

    private static String count(List<NcertErratum> errata, Predicate<NcertErratum> test) {
        return String.valueOf(errata.stream().filter(test).count());
    }
}
