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

    private final int dpi;

    PdfPageRenderer(int dpi) {
        this.dpi = dpi;
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
