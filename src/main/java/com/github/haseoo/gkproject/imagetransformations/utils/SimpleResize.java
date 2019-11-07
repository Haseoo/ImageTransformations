package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleResize {
    public static Image resizeImage(Image source, int scaleX, int scaleY) {
        Point newDimensions = calculateNewDimensions(source, scaleX, scaleY);
        return resize(source, newDimensions);
    }

    private static Point calculateNewDimensions(Image source, double scaleX, double scaleY) {
        return new Point((int)(source.getWidth() * scaleX / 100.0),
                (int) (source.getHeight() * scaleY / 100.0));
    }

    private static Image resize(Image source, Point newDimensions) {
        WritableImage newImage = new WritableImage(newDimensions.getX(), newDimensions.getY());
        val oldPixels = source.getPixelReader();
        val newPixels = newImage.getPixelWriter();
        double scaleX = source.getWidth() / newImage.getWidth();
        double scaleY = source.getHeight() / newImage.getHeight();
        for (int x = 0; x < newImage.getWidth(); x++) {
            for(int y = 0; y < newImage.getHeight(); y++) {
                newPixels.setColor(x, y, oldPixels.getColor((int)(x * scaleX), (int)(y * scaleY)));
            }
        }
        return newImage;
    }
}
