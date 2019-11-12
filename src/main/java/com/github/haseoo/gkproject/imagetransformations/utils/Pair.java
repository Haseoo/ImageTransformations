package com.github.haseoo.gkproject.imagetransformations.utils;

import lombok.Value;

@Value
public class Pair<T> {
    private T x;
    private T y;

    public T getWidth() {
        return x;
    }

    public T getHeight() {
        return y;
    }
}
