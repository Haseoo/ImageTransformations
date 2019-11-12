package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.function.Function;

import static com.github.haseoo.gkproject.imagetransformations.utils.Utils.getResourceURL;
import static javafx.stage.Modality.APPLICATION_MODAL;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JavaFXUtils {
    public static <T> T displayInputDialog(String fxmlReactivePath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getResourceURL(fxmlReactivePath));
        Stage stage = new Stage();
        stage.initModality(APPLICATION_MODAL);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.showAndWait();
        return fxmlLoader.getController();
    }

    public static Image copyImage(Image source) {
        return createImage(new Pair<>((int) source.getWidth(), (int) source.getHeight()),
                point -> source.getPixelReader().getColor(point.getX(), point.getY()));
    }

    public static Image createImage(Pair<Integer> dimensions, Function<Pair<Integer>, Color> calculateColor) {
        WritableImage image = new WritableImage(dimensions.getWidth(), dimensions.getHeight());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.getPixelWriter().setColor(x, y, calculateColor.apply(new Pair<>(x, y)));
            }
        }
        return image;
    }

    public static Image createImageUsingBilinearInterpolation(Image source, Pair dimensions) {
        final int oldWidth, oldHeight, newWidth, newHeight;
        oldHeight = (int) source.getHeight();
        oldWidth = (int) source.getWidth();
        newWidth = (int) dimensions.getWidth();
        newHeight = (int) dimensions.getHeight();
        WritableImage image = new WritableImage(newWidth, newHeight);

        double ratioX = (oldWidth * 1.0) / (newWidth * 1.0);
        double ratioY = (oldHeight * 1.0) / (newHeight * 1.0);
        double x, y, dx, dy;

        int firstX, firstY, secondX, secondY;

        double red1, red2, red3, red4, red5,
                green1, green2, green3, green4, green5,
                blue1, blue2, blue3, blue4, blue5;

        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                x = (i * ratioX);
                y = (j * ratioY);
                dx = i * ratioX - (int) (i * ratioX);
                dy = j * ratioY - (int) (j * ratioY);
                firstX = (int) x;
                firstY = (int) y;
                if (firstX + 1 >= oldWidth) {
                    secondX = firstX;
                } else {
                    secondX = firstX + 1;
                }
                if (firstY + 1 >= oldHeight) {
                    secondY = firstY;
                } else {
                    secondY = firstY + 1;
                }
                if (source.getPixelReader().getColor(firstX, firstY).getOpacity() == 0) {
                    continue;
                }
//                kolory pixeli sasiadujacych gornych
                red1 = source.getPixelReader().getColor(firstX, firstY).getRed();
                red2 = source.getPixelReader().getColor(secondX, firstY).getRed();
                green1 = source.getPixelReader().getColor(firstX, firstY).getGreen();
                green2 = source.getPixelReader().getColor(secondX, firstY).getGreen();
                blue1 = source.getPixelReader().getColor(firstX, firstY).getBlue();
                blue2 = source.getPixelReader().getColor(secondX, firstY).getBlue();
//                interpolacja pozioma dla gory
                red3 = ((red1 * (1 - dx)) + (red2 * dx));
                green3 = ((green1 * (1 - dx)) + (green2 * dx));
                blue3 = ((blue1 * (1 - dx)) + (blue2 * dx));

//                kolory pixeli sasiadujacych dolnych
                red1 = source.getPixelReader().getColor(firstX, secondY).getRed();
                red2 = source.getPixelReader().getColor(secondX, secondY).getRed();
                green1 = source.getPixelReader().getColor(firstX, secondY).getGreen();
                green2 = source.getPixelReader().getColor(secondX, secondY).getGreen();
                blue1 = source.getPixelReader().getColor(firstX, secondY).getBlue();
                blue2 = source.getPixelReader().getColor(secondX, secondY).getBlue();
//                interpolacja pozioma dla dolu
                red4 = ((red1 * (1 - dx)) + (red2 * dx));
                green4 = ((green1 * (1 - dx)) + (green2 * dx));
                blue4 = ((blue1 * (1 - dx)) + (blue2 * dx));

//                interpolacja pionowa
                red5 = ((red3 * (1 - dy)) + (red4 * dy));
                green5 = ((green3 * (1 - dy)) + (green4 * dy));
                blue5 = ((blue3 * (1 - dy)) + (blue4 * dy));
                Color color = Color.color(red5, green5, blue5);

                image.getPixelWriter().setColor(i, j, color);

            }
        }
        return image;
    }
}
