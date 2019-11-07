package com.github.haseoo.gkproject.imagetransformations.utils;

import lombok.Value;

@Value
public class Point {
    private int x;
    private int y;

    public int getWidth() {
        return x;
    }

    public int getHeight() {
        return y;
    }
}
