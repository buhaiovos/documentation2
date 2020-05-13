package edu.cad.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class Pair<T1, T2> {
    private final T1 left;
    private final T2 right;
}