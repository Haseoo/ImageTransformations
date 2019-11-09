package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.gkproject.imagetransformations.utils.JavaFXUtils.createImage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResize {
    public static Image simpleResize(Image source, Pair<Integer> scale) {
        Pair<Integer> newDimensions = calculateNewDimensions(source, scale.getX(), scale.getY());
        Pair<Double> newScale = calculateNewScale(new Pair<>(source.getWidth(), source.getHeight()), newDimensions);
        return createImage(newDimensions,
                point -> source.getPixelReader().getColor((int) (point.getX() * newScale.getX()),
                                                          (int) (point.getY() * newScale.getY())));
    }

    private static Pair<Integer> calculateNewDimensions(Image source, double scaleX, double scaleY) {
        return new Pair<>((int) (source.getWidth() * scaleX / 100.0),
                          (int) (source.getHeight() * scaleY / 100.0));
    }

    private static Pair<Double> calculateNewScale(Pair<Double> oldDimensions, Pair<Integer> newDimensions) {
        return new Pair<> (oldDimensions.getWidth() / (double) newDimensions.getWidth(),
                            oldDimensions.getHeight() / (double) newDimensions.getHeight());
    }
}
