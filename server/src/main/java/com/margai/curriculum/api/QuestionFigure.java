package com.margai.curriculum.api;

/**
 * A figure a question prints ({@code questions.figures}, migration V9): recorded at D19, cropped
 * and checked in a buffer before D31, and a question with a figure is never served without its
 * crop (founder's D19 ruling 4, DECISIONS 2026-10-20).
 *
 * @param at          {@code stem}, or {@code option:<key>} for a figure that is an option
 * @param page        the 1-based page of the source booklet it is printed on
 * @param description what it shows, in words, for the cropper and the reviewer
 */
public record QuestionFigure(String at, int page, String description) {
}
