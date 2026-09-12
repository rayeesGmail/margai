package com.margai.pipeline.internal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.function.ObjIntConsumer;
import javax.imageio.ImageIO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.filter.MissingImageReaderException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.PageDrawer;
import org.apache.pdfbox.rendering.PageDrawerParameters;

/**
 * One source PDF into page images (TECH_PLAN §6.1). The pages are handed to the caller one at a
 * time rather than returned as a list: a chapter is up to forty pages of bitmap, and holding a
 * whole book of them would be the one thing that makes this command need a big heap.
 *
 * <p><strong>A page that cannot be drawn completely is a failure, not a page.</strong> PDFBox's
 * default answer to an image it cannot decode is to log and carry on, leaving that image blank —
 * so a missing decoder silently produces pages the VISION tier then reads with a figure or an
 * equation missing, and nothing downstream can tell. NCERT triggered this twice on real books,
 * with two different formats (D14), which is why the guard here is not a list of formats: any
 * {@link MissingImageReaderException} from any operator stops the run.
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
            PDFRenderer renderer = new StrictRenderer(document);
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
        } catch (MissingImageReaderException e) {
            throw new UncheckedIOException("this PDF embeds an image format with no decoder on the "
                    + "classpath, and a page drawn without its images is worse than no page: " + e.getMessage(), e);
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

    /**
     * A renderer whose page drawer refuses to swallow a missing decoder. PDFBox routes an operator's
     * {@code IOException} through {@code operatorException}, whose default implementation logs it
     * and continues; this one rethrows the case that would otherwise cost us a blank figure.
     */
    private static final class StrictRenderer extends PDFRenderer {

        private StrictRenderer(PDDocument document) {
            super(document);
        }

        @Override
        protected PageDrawer createPageDrawer(PageDrawerParameters parameters) throws IOException {
            return new PageDrawer(parameters) {
                @Override
                protected void operatorException(Operator operator, List<COSBase> operands, IOException e)
                        throws IOException {
                    if (e instanceof MissingImageReaderException) {
                        throw e;
                    }
                    super.operatorException(operator, operands, e);
                }
            };
        }
    }

    @FunctionalInterface
    interface PageFilter {

        boolean rendered(int page);
    }
}
