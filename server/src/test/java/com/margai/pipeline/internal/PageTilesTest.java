package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

/**
 * Splitting a page into overlapping bands. The point of the exercise is that each band reaches the
 * model unscaled: a 150-DPI A4 page is about 1,754 pixels tall against the provider's ~1,568 limit,
 * so a whole page is shrunk before it is read, and the D14 audit found every transcription error in
 * a small glyph — a subscript, an exponent, a prime, a minus sign (D14).
 */
class PageTilesTest {

    private static final int WIDTH = 1240;
    private static final int HEIGHT = 1754;

    @Test
    void oneTileIsThePageUntouched() {
        byte[] page = png(WIDTH, HEIGHT);

        assertThat(PageTiles.split(page, 1)).containsExactly(page);
        assertThat(PageTiles.split(page, 0)).containsExactly(page);
    }

    /**
     * The whole page first, for layout, then the bands, for glyphs: chapter 7 extracted each way
     * on its own was wrong in one place — bands cut sentences and misordered a two-column page,
     * the whole page alone lost every prime (D14).
     */
    @Test
    void theWholePageComesFirstThenTwoTilesCoverItWithAnOverlapInReadingOrder() throws IOException {
        byte[] page = png(WIDTH, HEIGHT);
        List<byte[]> images = PageTiles.split(page, 2);

        assertThat(images).hasSize(3);
        // Last, not first: with the page first the model read its characters off it and lost the
        // primes, exactly as with the page alone (D14, chapter 7, 21:32).
        assertThat(images.getLast()).as("the page itself, untouched, after the bands").isEqualTo(page);
        List<byte[]> bands = images.subList(0, 2);
        int overlap = (int) Math.round(HEIGHT * PageTiles.OVERLAP);
        assertThat(height(bands.get(0))).isEqualTo(HEIGHT / 2 + overlap);
        assertThat(height(bands.get(1))).isEqualTo(HEIGHT - HEIGHT / 2 + overlap);
        assertThat(height(bands.get(0)) + height(bands.get(1)))
                .as("the bands together cover the page, twice over the seam")
                .isEqualTo(HEIGHT + 2 * overlap);
        assertThat(width(bands.get(0))).isEqualTo(WIDTH);
    }

    /** The reason the bands exist: each is under the size the provider would shrink; the page is not. */
    @Test
    void eachBandOfARealPageIsBelowTheProvidersResizeThreshold() throws IOException {
        int providerLimit = 1568;
        assertThat(HEIGHT).as("a whole page is over the limit, which is the problem")
                .isGreaterThan(providerLimit);

        List<byte[]> images = PageTiles.split(png(WIDTH, HEIGHT), 2);
        assertThat(height(images.getLast())).as("the whole page travels as it is, and is shrunk")
                .isGreaterThan(providerLimit);
        for (byte[] band : images.subList(0, images.size() - 1)) {
            assertThat(Math.max(width(band), height(band)))
                    .as("a band must arrive unscaled")
                    .isLessThanOrEqualTo(providerLimit);
        }
    }

    @Test
    void theSeamIsWideEnoughToCarryWholeLinesOfBodyText() {
        // ~11pt text at 150 DPI is about 23 pixels a line; the seam must hold several.
        assertThat(HEIGHT * PageTiles.OVERLAP).isGreaterThan(3 * 23);
    }

    private static byte[] png(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().setColor(Color.WHITE);
        image.getGraphics().fillRect(0, 0, width, height);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bytes);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return bytes.toByteArray();
    }

    private static int height(byte[] png) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(png)).getHeight();
    }

    private static int width(byte[] png) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(png)).getWidth();
    }
}
