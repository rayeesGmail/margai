package com.margai.ai.tasks;

/**
 * One numbered item of a verify call: what a paragraph prints on the page being read (D15).
 *
 * @param number                     the item's number in the call, from 1
 * @param text                       the part of the paragraph printed on this page
 * @param continuesFromPreviousPage  its beginning is printed on the previous page
 * @param continuesOnNextPage        it runs on to the next page
 */
public record VerifyItem(int number, String text, boolean continuesFromPreviousPage, boolean continuesOnNextPage) {
}
