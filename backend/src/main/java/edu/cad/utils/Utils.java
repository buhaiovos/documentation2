package edu.cad.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.OptionalInt;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {
    public static boolean isNumber(String value) {
        return NumberUtils.isParsable(value);
    }

    public static OptionalInt parseInteger(String value) {
        return NumberUtils.isParsable(value) ? OptionalInt.of(Integer.parseInt(value)) : OptionalInt.empty();
    }

    public static <T, R> R nullOr(T obj, Function<T, R> getter) {
        return ofNullable(obj)
                .map(getter)
                .orElse(null);
    }
}

