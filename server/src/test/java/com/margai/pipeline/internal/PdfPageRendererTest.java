package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
 */
class PdfPageRendererTest {

    /** The founder's inputs, git-ignored; present on a machine that has them, absent in CI. */
    private static final Path NCERT = Path.of("..", "ncert", "2022-ed", "en");

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
     */
    @Test
    void everyPageOfBothPilotBooksRendersWithNoMissingDecoder() throws IOException {
        assumeTrue(Files.isDirectory(NCERT), "founder's NCERT PDFs not on this machine");

        int pages = 0;
        for (String book : List.of("phy11-part1", "bio11")) {
            try (var files = Files.list(NCERT.resolve(book))) {
                for (Path chapter : files.filter(path -> path.toString().endsWith(".pdf")).sorted().toList()) {
                    byte[] pdf = Files.readAllBytes(chapter);
                    int[] drawn = {0};
                    // 72 DPI: this is a decoder sweep, not a quality check — it only has to draw.
                    new PdfPageRenderer(72).render(pdf, page -> false, (png, page) -> drawn[0]++);
                    assertThat(drawn[0]).as("pages drawn of %s", chapter.getFileName()).isPositive();
                    pages += drawn[0];
                }
            }
        }
        assertThat(pages).isGreaterThan(400);
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
        List<byte[]> pages = new ArrayList<>();
        int count = new PdfPageRenderer(150).render(pdf, page -> page > 4, (png, page) -> pages.add(png));

        assertThat(count).isGreaterThan(4);
        assertThat(pages).hasSize(4);
        String dump = System.getProperty("render.dump");
        if (dump != null) {
            Files.write(Path.of(dump), pages.get(3));
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
