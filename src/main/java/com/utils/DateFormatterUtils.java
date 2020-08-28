package com.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Date formatter utils.
 */
public final class DateFormatterUtils {

    private static final DateTimeFormatter DATE_FORMATTER = ofPattern("yyyy-MM-dd");

    /**
     * Parses date from 'yyyy-MM-dd' pattern.
     *
     * @param formattedString the string that contains date in 'yyyy-MM-dd' format.
     * @return LocalDate value.
     */
    public static LocalDate parseDate(final String formattedString) {
        return parse(formattedString, DATE_FORMATTER);
    }

    /**
     * Converts a LocalDate to String in 'yyyy-MM-dd' pattern.
     *
     * @param date LocalDate for transformation.
     * @return date in string format.
     */
    public static String formatDate(final LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
}
