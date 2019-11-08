package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.haseoo.gkproject.imagetransformations.utils.JavaFXUtils.createImage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResize {
    public static Image simpleResize(Image source, Point scale) {
        Point newDimensions = calculateNewDimensions(source, scale.getX(), scale.getY());
        double scaleX = source.getWidth() / (double) newDimensions.getWidth();
        double scaleY = source.getHeight() / (double) newDimensions.getHeight();
        return createImage(newDimensions, point -> source.getPixelReader().getColor((int) (point.getX() * scaleX), (int) (point.getY() * scaleY)));
    }

    private static Point calculateNewDimensions(Image source, double scaleX, double scaleY) {
        return new Point((int) (source.getWidth() * scaleX / 100.0),
                (int) (source.getHeight() * scaleY / 100.0));
    }
}
