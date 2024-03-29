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
}
