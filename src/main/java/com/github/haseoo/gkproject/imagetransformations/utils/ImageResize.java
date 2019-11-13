package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import static com.github.haseoo.gkproject.imagetransformations.utils.JavaFXUtils.createImage;
import static javafx.scene.paint.Color.color;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResize {
    public static Image simpleResize(Image source, Pair<Integer> scale) {
        Pair<Integer> newDimensions = calculateNewDimensions(source, scale.getX(), scale.getY());
        Pair<Double> newScale = calculateNewScale(new Pair<>(source.getWidth(), source.getHeight()), newDimensions);
        return createImage(newDimensions,
                point -> source.getPixelReader().getColor((int) (point.getX() * newScale.getX()),
                        (int) (point.getY() * newScale.getY())));
    }

    public static Image bilinearResize(Image sourceImage, Pair<Integer> scale) {
        Pair<Integer> newDimensions = calculateNewDimensions(sourceImage, scale.getWidth(), scale.getHeight());
        Pair<Integer> oldDimensions = new Pair<>((int) sourceImage.getWidth(), (int) sourceImage.getHeight());
        val source = sourceImage.getPixelReader();
        Pair<Double> ratio = new Pair<>((oldDimensions.getWidth() * 1.0) / (newDimensions.getWidth() * 1.0),
                (oldDimensions.getHeight() * 1.0) / (newDimensions.getHeight() * 1.0));
        return createImage(newDimensions, point -> {
            Pair<Double> diff = new Pair<>(point.getX() * ratio.getX() - (int) (point.getX() * ratio.getX()),
                    point.getY() * ratio.getY() - (int) (point.getY() * ratio.getY()));
            Pair<Integer> first = new Pair<>((int) (point.getX() * ratio.getX()), (int)(point.getY() * ratio.getY()));
            Pair<Integer> second = getSecondPoint(oldDimensions, first);
            return interpolateColor(source, diff, first, second);
        });
    }

    private static Pair<Integer> calculateNewDimensions(Image source, double scaleX, double scaleY) {
        return new Pair<>((int) (source.getWidth() * scaleX / 100.0),
                (int) (source.getHeight() * scaleY / 100.0));
    }

    private static Pair<Double> calculateNewScale(Pair<Double> oldDimensions, Pair<Integer> newDimensions) {
        return new Pair<>(oldDimensions.getWidth() / (double) newDimensions.getWidth(),
                oldDimensions.getHeight() / (double) newDimensions.getHeight());
    }

    private static Color interpolateColor(PixelReader source, Pair<Double> diff, Pair<Integer> firstPoint, Pair<Integer> secondPoint) {
        Pair<Color> upperColors = getColorPair(source, firstPoint, secondPoint, firstPoint.getY());
        Pair<Color> lowerColors = getColorPair(source, firstPoint, secondPoint, secondPoint.getY());

        Color interpolatedUpperColor = getEffectiveColor(diff, upperColors.getX(), upperColors.getY());
        Color interpolatedLowerColor = getEffectiveColor(diff, lowerColors.getX(), lowerColors.getY());

        return getEffectiveColor(diff, interpolatedUpperColor, interpolatedLowerColor);
    }

    private static Pair<Color> getColorPair(PixelReader source, Pair<Integer> firstPoint, Pair<Integer> secondPoint, Integer y) {
        return new Pair<>(color((source.getColor(firstPoint.getX(), y).getRed()),
                source.getColor(firstPoint.getX(), y).getGreen(),
                source.getColor(firstPoint.getX(), y).getBlue()),
                color(source.getColor(secondPoint.getX(), y).getRed(),
                        source.getColor(secondPoint.getX(), y).getGreen(),
                        source.getColor(secondPoint.getX(), y).getBlue()));
    }

    private static Color getEffectiveColor(Pair<Double> diff, Color color1, Color color2) {
        return color((color1.getRed() * (1 - diff.getX())) + (color2.getRed() * diff.getX()),
                (color1.getGreen() * (1 - diff.getX())) + (color2.getGreen() * diff.getX()),
                (color1.getBlue() * (1 - diff.getX())) + (color2.getBlue() * diff.getX()));
    }

    private static Pair<Integer> getSecondPoint(Pair<Integer> oldDimensions, Pair<Integer> first) {
        int secondX;
        int secondY;
        if (first.getX() + 1 >= oldDimensions.getWidth()) {
            secondX = first.getX();
        } else {
            secondX = first.getX() + 1;
        }
        if (first.getY() + 1 >= oldDimensions.getHeight()) {
            secondY = first.getY();
        } else {
            secondY = first.getY() + 1;
        }
        return new Pair<>(secondX, secondY);
    }
}
