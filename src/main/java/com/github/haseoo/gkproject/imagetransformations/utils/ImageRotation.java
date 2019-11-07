package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.NO_OFFSET;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageRotation {
    public static Image rotateImage(Image image, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        Point offset = getOffset(sin, cos, new Point((int) image.getWidth(), (int) image.getHeight()));
        Point imageSize = getImageSize(image, sin, cos);
        return rotateImage(image, sin, cos, offset, imageSize);
    }

    private static Image rotateImage(Image image, double sin, double cos, Point offset, Point imageSize) {
        WritableImage newImage = new WritableImage(imageSize.getX(), imageSize.getY());
        val oldPixels = image.getPixelReader();
        val newPixels = newImage.getPixelWriter();
        for (int x = 0; x < imageSize.getWidth(); x++) {
            for (int y = 0; y < imageSize.getHeight(); y++) {
                int effectiveX = x - offset.getX();
                int effectiveY = y - offset.getY();
                Point originalPoint = calculateRotation(new Point(effectiveX, effectiveY), sin, cos);
                if (originalPoint.getX() > 0 && originalPoint.getY() > 0 &&
                        originalPoint.getX() < image.getWidth() && originalPoint.getY() < image.getHeight()) {
                    newPixels.setColor(x, y, oldPixels.getColor(originalPoint.getX(), originalPoint.getY()));
                } else {
                    newPixels.setColor(x, y, Color.WHITE);
                }
            }
        }
        return newImage;
    }

    private static Point getImageSize(Image image, double sin, double cos) {

        return new Point((int) Math.ceil(Math.abs(image.getWidth() * cos) + Math.abs(image.getHeight() * sin)),
                (int) Math.ceil(Math.abs(image.getHeight() * cos) + Math.abs(image.getWidth() * sin))
        );
    }

    private static Point getOffset(double sin, double cos, Point dimensions) {
        if (sin >= 0 && cos >= 0) {
            return new Point(NO_OFFSET, (int) (dimensions.getWidth() * sin));
        }
        if (sin <= 0 && cos <= 0) {
            return new Point((int) Math.round(cos * (-dimensions.getWidth()) - sin * dimensions.getHeight()),
                    (int) Math.round((-dimensions.getHeight()) * cos));
        }
        if (sin >= 0 && cos <= 0) {
            return new Point((int) Math.round(dimensions.getWidth() * (-cos)),
                    (int) Math.round(dimensions.getWidth() * sin - dimensions.getHeight() * cos));
        }
        return new Point((int) Math.round(sin * (-dimensions.getHeight())), NO_OFFSET);
    }

    private static Point calculateRotation(Point point, double sin, double cos) {
        return new Point((int) Math.ceil(point.getX() * cos - point.getY() * sin),
                (int) Math.ceil(point.getX() * sin + point.getY() * cos));
    }

}
