package com.margai.ai.tasks;

import java.math.BigDecimal;
import java.util.List;

/**
 * Output record of the {@code ncert_extract} prompt (TECH_PLAN §6.3): one page's paragraphs at
 * their printed addresses, plus the model's confidence in having read the page correctly.
 *
 * <p>The chapter number is absent on purpose — it comes from the founder-reviewed
 * {@code books.yaml}, not from the model (DECISIONS D14).
 *
 * @param paragraphs in reading order, possibly empty for a page that is all figure or cover
 * @param confidence 0–1, how sure the model is of this page's text and addresses
 */
public record NcertPage(List<Paragraph> paragraphs, BigDecimal confidence) {

    public NcertPage {
        paragraphs = paragraphs == null ? List.of() : List.copyOf(paragraphs);
    }

    /**
     * One paragraph as printed.
     *
     * @param section      the numbered heading it falls under ("7.9"), or the chapter's own
     *                     number for text before the first numbered section
     * @param paraNo       its position within that section, from 1
     * @param text         the paragraph, transcribed, never summarised
     * @param hasEquations whether it contains a mathematical or chemical expression
     * @param figureRefs   figure and table labels the paragraph refers to, as printed
     */
    public record Paragraph(
            String section,
            int paraNo,
            String text,
            boolean hasEquations,
            List<String> figureRefs) {

        public Paragraph {
            figureRefs = figureRefs == null ? List.of() : List.copyOf(figureRefs);
        }
    }
}
