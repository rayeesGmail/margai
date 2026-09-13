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
     * The whole page first, then {@code tiles} overlapping bands of it, top to bottom. One tile
     * means the page alone, which is the configuration this pipeline shipped with.
     *
     * <p>Both, not either, because chapter 7 was extracted both ways on 2026-09-13 and each was
     * wrong in one place. Bands alone kept every prime and cut sentences at the seam, and on a
     * two-column page with a worked example put a mid-page paragraph first. The whole page alone
     * read the layout correctly and lost the primes — {@code F'_GB} came back {@code F_GB} — which
     * the text layer cannot restore, because NCERT's Symbol font has no Unicode mapping for them.
     * So the page supplies layout and reading order and the bands supply the glyphs, and the prompt
     * says which is which (D14, DECISIONS).
     *
     * <p>The bands come first and the page last, not the other way round: with the page first the
     * primes vanished exactly as they had with the page alone — the model reads its characters
     * off the first image it is given, whatever it is told about the rest.
     */
    static List<byte[]> split(byte[] png, int tiles) {
        if (tiles <= 1) {
            return List.of(png);
        }
        BufferedImage page = read(png);
        int height = page.getHeight();
        int band = height / tiles;
        int overlap = (int) Math.round(height * OVERLAP);

        List<byte[]> bands = new ArrayList<>(tiles + 1);
        for (int index = 0; index < tiles; index++) {
            int top = Math.max(0, index * band - (index == 0 ? 0 : overlap));
            int bottom = index == tiles - 1 ? height : Math.min(height, (index + 1) * band + overlap);
            bands.add(write(page.getSubimage(0, top, page.getWidth(), bottom - top)));
        }
        bands.add(png);
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
