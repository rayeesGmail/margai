package com.margai.pipeline.internal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.ObjIntConsumer;
import javax.imageio.ImageIO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * One source PDF into page images (TECH_PLAN §6.1). The pages are handed to the caller one at a
 * time rather than returned as a list: a chapter is up to forty pages of bitmap, and holding a
 * whole book of them would be the one thing that makes this command need a big heap.
 */
final class PdfPageRenderer {

    static final String MEDIA_TYPE = "image/png";

    private static final String FORMAT = "png";

    /**
     * The image formats NCERT's PDFs embed that Java cannot decode out of the box. PDFBox reacts to
     * a missing decoder by logging and rendering the page <em>without</em> that image, so the whole
     * corpus would degrade silently: 21 of the 30 chapter files in the two pilot books carry
     * JPEG2000. This is a tripwire on the classpath, checked once, loudly (found on the first real
     * render, D14).
     */
    private static final String JPEG_2000 = "jpeg2000";

    private final int dpi;

    PdfPageRenderer(int dpi) {
        requireImageReader(JPEG_2000);
        this.dpi = dpi;
    }

    private static void requireImageReader(String format) {
        if (!ImageIO.getImageReadersByFormatName(format).hasNext()) {
            throw new IllegalStateException("no ImageIO reader for " + format
                    + ": NCERT pages embed " + format + " images and PDFBox would render them blank "
                    + "rather than fail. Check that jai-imageio-" + format + " is on the classpath.");
        }
    }

    /**
     * Renders every page, calling {@code page} with the PNG bytes and the 1-based page number.
     * {@code skip} decides which pages are rendered at all, so a re-run pays nothing for the
     * pages it already wrote. Returns how many pages the document has, rendered or not.
     */
    int render(byte[] pdf, PageFilter skip, ObjIntConsumer<byte[]> page) {
        try (PDDocument document = Loader.loadPDF(pdf)) {
            PDFRenderer renderer = new PDFRenderer(document);
            int count = document.getNumberOfPages();
            for (int index = 0; index < count; index++) {
                int number = index + 1;
                if (skip.rendered(number)) {
                    continue;
                }
                BufferedImage image = renderer.renderImageWithDPI(index, dpi, ImageType.RGB);
                page.accept(png(image), number);
            }
            return count;
        } catch (IOException e) {
            throw new UncheckedIOException("cannot render the PDF", e);
        }
    }

    private static byte[] png(BufferedImage image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, FORMAT, bytes);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot encode the page as PNG", e);
        }
        return bytes.toByteArray();
    }

    @FunctionalInterface
    interface PageFilter {

        boolean rendered(int page);
    }
}
