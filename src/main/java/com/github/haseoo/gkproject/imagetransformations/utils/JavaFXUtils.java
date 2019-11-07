package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import java.io.IOException;

import static com.github.haseoo.gkproject.imagetransformations.utils.Utils.getResourceURL;
import static javafx.stage.Modality.APPLICATION_MODAL;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JavaFXUtils {
    public static <T> T displayInputDialog(String fxmlReactivePath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getResourceURL(fxmlReactivePath));
        Parent parent = fxmlLoader.load();
        T dialogController = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        return dialogController;
    }
}
