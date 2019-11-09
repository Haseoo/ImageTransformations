package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.scene.image.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.NO_OFFSET;
import static com.github.haseoo.gkproject.imagetransformations.utils.JavaFXUtils.createImage;
import static javafx.scene.paint.Color.TRANSPARENT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageRotation {
    public static Image rotateImage(Image image, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        Pair<Integer> offset = getOffset(sin, cos, new Pair<>((int) image.getWidth(), (int) image.getHeight()));
        Pair<Integer> imageSize = getImageSize(image, sin, cos);
        return rotateImage(image, sin, cos, offset, imageSize);
    }

    private static Image rotateImage(Image image, double sin, double cos, Pair<Integer> offset, Pair<Integer> imageSize) {
        val oldPixels = image.getPixelReader();
        return createImage(imageSize, point -> {
            int effectiveX = point.getX() - offset.getX();
            int effectiveY = point.getY() - offset.getY();
            Pair<Integer> originalPoint = calculateRotation(new Pair<>(effectiveX, effectiveY), sin, cos);
            if (originalPoint.getX() > 0 && originalPoint.getY() > 0 &&
                    originalPoint.getX() < image.getWidth() && originalPoint.getY() < image.getHeight()) {
                return oldPixels.getColor(originalPoint.getX(), originalPoint.getY());
            }
            return TRANSPARENT;
        });
    }

    private static Pair<Integer> getImageSize(Image image, double sin, double cos) {

        return new Pair<>((int) Math.ceil(Math.abs(image.getWidth() * cos) + Math.abs(image.getHeight() * sin)),
                (int) Math.ceil(Math.abs(image.getHeight() * cos) + Math.abs(image.getWidth() * sin))
        );
    }

    private static Pair<Integer> getOffset(double sin, double cos, Pair<Integer> dimensions) {
        if (sin >= 0 && cos >= 0) {
            return new Pair<>(NO_OFFSET, (int) (dimensions.getWidth() * sin));
        }
        if (sin <= 0 && cos <= 0) {
            return new Pair<>((int) Math.round(cos * (-dimensions.getWidth()) - sin * dimensions.getHeight()),
                    (int) Math.round((-dimensions.getHeight()) * cos));
        }
        if (sin >= 0 && cos <= 0) {
            return new Pair<>((int) Math.round(dimensions.getWidth() * (-cos)),
                    (int) Math.round(dimensions.getWidth() * sin - dimensions.getHeight() * cos));
        }
        return new Pair<>((int) Math.round(sin * (-dimensions.getHeight())), NO_OFFSET);
    }

    private static Pair<Integer> calculateRotation(Pair<Integer> point, double sin, double cos) {
        return new Pair<>((int) Math.ceil(point.getX() * cos - point.getY() * sin),
                (int) Math.ceil(point.getX() * sin + point.getY() * cos));
    }

}
