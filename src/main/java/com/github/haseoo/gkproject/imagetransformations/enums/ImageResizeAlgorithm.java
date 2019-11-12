package com.github.haseoo.gkproject.imagetransformations.enums;

import com.github.haseoo.gkproject.imagetransformations.utils.ImageResize;
import com.github.haseoo.gkproject.imagetransformations.utils.Pair;
import javafx.scene.image.Image;

import java.util.function.BiFunction;

public enum ImageResizeAlgorithm {
    BILINEAR_RESIZE (ImageResize::bilinearResize),
    SIMPLE_RESIZE (ImageResize::simpleResize);

    ImageResizeAlgorithm(BiFunction<Image, Pair<Integer>, Image> function) {
        this.function = function;
    }
    public final BiFunction<Image, Pair<Integer>, Image> function;
}
