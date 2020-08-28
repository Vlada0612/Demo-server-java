package com.utils;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Stream utils.
 */
public final class StreamUtils {

    /**
     * List conversion by function.
     *
     * @param list        list to map.
     * @param mapFunction function for map.
     * @param <T>         result list type.
     * @param <S>         list type.
     * @return list with T type.
     */
    public static <T, S> List<T> listConversion(final List<S> list, final Function<S, T> mapFunction) {
        return list.stream().map(mapFunction).collect(toList());
    }
}
