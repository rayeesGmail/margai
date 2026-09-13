package com.margai.pipeline.internal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * One page image as overlapping horizontal bands, so small glyphs survive the trip to the model.
 *
 * <p>The provider resizes any image whose long edge exceeds about 1,568 pixels. A 150-DPI A4 page
 * is ~1,754, so a whole page is shrunk <em>before the model sees it</em> and a subscript ends up a
 * few pixels tall — which is exactly where the D14 audit found every error: {@code 1} read as
 * {@code l} or {@code i}, a dropped exponent, a lost prime, a missing minus sign, while the prose
 * around them was transcribed perfectly. Raising the render DPI cannot help, because the cap is
 * downstream of us. Splitting the page does: each band is under the cap, so it arrives unscaled.
 *
 * <p>The bands overlap, because a horizontal cut lands wherever it lands — including through the
 * middle of a line. The overlap gives the model the whole of any paragraph the cut crosses, and the
 * prompt tells it not to transcribe the shared text twice.
 */
final class PageTiles {

    /** Share of the page height repeated at each seam. Enough for two or three lines of body text. */
    static final double OVERLAP = 0.06;

    private static final String FORMAT = "png";

    private PageTiles() {
    }

    /**
     * Splits a page into {@code tiles} overlapping bands, top to bottom. One tile means the page
     * unchanged, which is the configuration this pipeline shipped with and the one to fall back to.
     *
     * <p>Bands only — no whole-page image alongside them — and that was measured, not assumed.
     * On the evening of 2026-09-13 chapter 7 was extracted four ways. Bands alone: every prime
     * kept, one two-column page with a worked example read out of order. The whole page alone:
     * the layout right and every prime gone, {@code F'_GB} arriving as {@code F_GB}. Then the page
     * <em>with</em> the bands, three times — full size first, full size last, and as a 700-pixel
     * thumbnail too small to read a letter from — and the primes vanished all three times, with
     * the layout right and every label kept. Any whole-page view at all, even an illegible one,
     * makes the model transcribe from it rather than from the bands it is told to read. A prime
     * that the Symbol font never mapped cannot be restored from the text layer, so the primes
     * decide it: bands only, and the reading-order case goes to the page-image second read
     * (D14, DECISIONS).
     */
    static List<byte[]> split(byte[] png, int tiles) {
        if (tiles <= 1) {
            return List.of(png);
        }
        BufferedImage page = read(png);
        int height = page.getHeight();
        int band = height / tiles;
        int overlap = (int) Math.round(height * OVERLAP);

        List<byte[]> bands = new ArrayList<>(tiles);
        for (int index = 0; index < tiles; index++) {
            int top = Math.max(0, index * band - (index == 0 ? 0 : overlap));
            int bottom = index == tiles - 1 ? height : Math.min(height, (index + 1) * band + overlap);
            bands.add(write(page.getSubimage(0, top, page.getWidth(), bottom - top)));
        }
        return List.copyOf(bands);
    }

    private static BufferedImage read(byte[] png) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(png));
            if (image == null) {
                throw new IllegalArgumentException("not a readable image");
            }
            return image;
        } catch (IOException e) {
            throw new UncheckedIOException("cannot read the page image", e);
        }
    }

    private static byte[] write(BufferedImage image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, FORMAT, bytes);
        } catch (IOException e) {
            throw new UncheckedIOException("cannot encode the page band as PNG", e);
        }
        return bytes.toByteArray();
    }
}
