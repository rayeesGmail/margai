package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.margai.curriculum.api.BookLanguage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

/**
 * The renderer's decoder tripwire and, when the founder's PDFs are on this machine, a real NCERT
 * page rendered end to end.
 *
 * <p>The first run against a real book failed here: NCERT embeds JPEG2000 images, Java has no
 * decoder for them, and PDFBox answers a missing decoder by logging and drawing the page without
 * the image — so 21 of the 30 chapter files in the two pilot books would have produced pages with
 * their figures silently blank, and the VISION tier would have read them without ever knowing (D14).
 *
 * <p>The sweep is the free pre-flight the runbook requires before a book is rendered for the first
 * time, and it reads {@code books.yaml} so it covers exactly the chapter PDFs {@code ncert render}
 * will render — no more (a pass on an appendix nobody renders proves nothing) and no less (a
 * chapter named in the yaml but missing from disk would otherwise fail mid-run on a founder-run
 * command that costs money). {@code -Dncert.preflight} chooses the books; the default is the two
 * pilots, because all ten are ~1,690 pages and some minutes, and {@code ./mvnw verify} runs before
 * every commit (D15).
 */
class PdfPageRendererTest {

    /** The founder's inputs, git-ignored; present on a machine that has them, absent in CI. */
    private static final Path NCERT = Path.of("..", "ncert", "2022-ed", "en");

    /** Founder-owned and committed, so it is readable in CI even where the PDFs are not. */
    private static final Path BOOKS_YAML = Path.of("..", "pipeline", "inputs", "books.yaml");

    /** Unset = the two pilots, {@code all} = every book, otherwise a comma-separated code list. */
    private static final String PREFLIGHT = "ncert.preflight";

    private static final List<String> PILOTS = List.of("phy11-part1", "bio11");

    @Test
    void theDecodersNcertNeedsAreOnTheClasspath() {
        assertThat(ImageIO.getImageReadersByFormatName("jpeg2000").hasNext())
                .as("jai-imageio-jpeg2000 must stay on the classpath").isTrue();
        assertThat(ImageIO.getImageReadersByFormatName("jbig2").hasNext())
                .as("jbig2-imageio must stay on the classpath").isTrue();
    }

    /**
     * The real guarantee is not a list of formats — it is that a page PDFBox cannot draw completely
     * fails instead of arriving blank. Both decoder gaps were found on real books, one after the
     * other, and the second one was found *after* a format-specific tripwire had been added (D14).
     *
     * <p>Every chapter is swept before anything is reported: at ten books this runs for minutes, so
     * failing on the first bad file would mean one run per bad book. The per-book page counts are
     * printed rather than asserted — they are what the founder checks against the manifest before
     * paying to render, and a reprint that changed them should not fail a decoder test.
     */
    @Test
    void everyPageOfEverySelectedBookRendersWithNoMissingDecoder() {
        assumeTrue(Files.exists(NCERT.resolve("phy11-part1/keph107.pdf")), "founder's NCERT PDFs not on this machine");

        List<BookDefinition> books = selected(BooksYamlReader.read(BOOKS_YAML), System.getProperty(PREFLIGHT, ""));
        List<String> failures = new ArrayList<>();
        StringBuilder table = new StringBuilder("%nncert pre-flight — %d book(s) of books.yaml, en:%n".formatted(books.size()));
        int swept = 0;
        for (BookDefinition book : books) {
            if (!book.has(BookLanguage.en)) {
                table.append("  %-14s  no english edition in books.yaml%n".formatted(book.code()));
                continue;
            }
            int pages = 0;
            for (BookDefinition.Chapter chapter : book.chapters()) {
                pages += sweep(book, chapter, failures);
            }
            swept += pages;
            table.append("  %-14s %2d ch %5d pages%n".formatted(book.code(), book.chapters().size(), pages));
        }
        table.append("  %-14s %2s %5d pages%n".formatted("TOTAL", "", swept));
        System.out.print(table);

        assertThat(failures).as("chapters PDFBox could not draw completely%s", table).isEmpty();
        assertThat(swept).as("pages swept").isPositive();
    }

    /** One chapter drawn at 72 DPI — a decoder sweep, not a quality check; it only has to draw. */
    private static int sweep(BookDefinition book, BookDefinition.Chapter chapter, List<String> failures) {
        Path pdf = NCERT.resolve(book.code()).resolve(chapter.fileEn());
        if (!Files.exists(pdf)) {
            failures.add("%s/%s: named by books.yaml, not on this machine".formatted(book.code(), chapter.fileEn()));
            return 0;
        }
        try {
            int[] drawn = {0};
            int count = new PdfPageRenderer(72).render(Files.readAllBytes(pdf), page -> false, (png, page) -> drawn[0]++);
            if (drawn[0] != count) {
                failures.add("%s/%s: drew %d of its %d pages".formatted(book.code(), chapter.fileEn(), drawn[0], count));
            }
            return drawn[0];
        } catch (IOException | RuntimeException e) {
            failures.add("%s/%s: %s: %s".formatted(book.code(), chapter.fileEn(),
                    e.getClass().getSimpleName(), e.getMessage()));
            return 0;
        }
    }

    /**
     * The books the property names, in {@code books.yaml} order for {@code all} and in the order
     * given otherwise. A code the yaml does not carry is refused rather than quietly sweeping
     * nothing: an inert selector that reports success is the failure mode {@code --chapters}
     * already cost this pipeline one paid run (D15).
     */
    static List<BookDefinition> selected(List<BookDefinition> all, String property) {
        String value = property == null ? "" : property.trim();
        if (value.equals("all")) {
            return all;
        }
        List<String> codes = value.isEmpty() ? PILOTS
                : Arrays.stream(value.split(",")).map(String::trim).filter(code -> !code.isEmpty()).toList();
        Map<String, BookDefinition> byCode = new LinkedHashMap<>();
        all.forEach(book -> byCode.put(book.code(), book));
        List<String> unknown = codes.stream().filter(code -> !byCode.containsKey(code)).toList();
        if (codes.isEmpty() || !unknown.isEmpty()) {
            throw new IllegalArgumentException("-D%s=%s names no book of books.yaml%s; it takes 'all' or a comma-separated list of %s"
                    .formatted(PREFLIGHT, value, unknown.isEmpty() ? "" : " " + unknown, byCode.keySet()));
        }
        return codes.stream().map(byCode::get).toList();
    }

    @Test
    void theDefaultSweepIsTheTwoPilotBooks() {
        assertThat(selected(BooksYamlReader.read(BOOKS_YAML), "").stream().map(BookDefinition::code))
                .containsExactlyElementsOf(PILOTS);
    }

    @Test
    void allSweepsEveryBookOfBooksYaml() {
        List<BookDefinition> all = BooksYamlReader.read(BOOKS_YAML);
        assertThat(selected(all, "all")).isEqualTo(all);
        assertThat(selected(all, " all ")).isEqualTo(all);
    }

    @Test
    void namedBooksAreSweptInTheOrderGiven() {
        assertThat(selected(BooksYamlReader.read(BOOKS_YAML), "bio12, phy11-part2").stream().map(BookDefinition::code))
                .containsExactly("bio12", "phy11-part2");
    }

    /** The whole point of naming the known codes: a typo must stop the run, not shrink it. */
    @Test
    void anUnknownBookCodeIsRefusedRatherThanSweepingNothing() {
        List<BookDefinition> all = BooksYamlReader.read(BOOKS_YAML);
        assertThatThrownBy(() -> selected(all, "bio11,bio-11"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bio-11")
                .hasMessageContaining("phy11-part1");
        assertThatThrownBy(() -> selected(all, ",")).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Renders the first pages of a chapter. {@code -Drender.pdf=<book/file.pdf>} picks another
     * chapter and {@code -Drender.dump=<path>} writes the last page out, which is how a page is put
     * in front of a human eye when extraction quality is in question (D14–D16).
     */
    @Test
    void rendersARealNcertPageWithItsJpeg2000Figures() throws IOException {
        Path chapter = NCERT.resolve(System.getProperty("render.pdf", "phy11-part1/keph101.pdf"));
        assumeTrue(Files.exists(chapter), "founder's NCERT PDFs not on this machine");

        byte[] pdf = Files.readAllBytes(chapter);
        int wanted = Integer.getInteger("render.page", 4);
        List<byte[]> pages = new ArrayList<>();
        int count = new PdfPageRenderer(150).render(pdf, page -> page > wanted, (png, page) -> pages.add(png));

        assertThat(count).isGreaterThanOrEqualTo(wanted);
        assertThat(pages).hasSize(wanted);
        String dump = System.getProperty("render.dump");
        if (dump != null) {
            Files.write(Path.of(dump), pages.getLast());
        }
        for (byte[] png : pages) {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(png));
            assertThat(image).isNotNull();
            // 150 DPI on an NCERT page: wide enough to read, under the provider's 1568-pixel edge.
            assertThat(image.getWidth()).isBetween(1000, 1600);
            assertThat(image.getHeight()).isBetween(1400, 2000);
        }
    }
}
