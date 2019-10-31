package com.github.haseoo.gkproject.imagetransformations;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.APPLICATION_NAME;
import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.MAIN_WINDOW_FXML_PATH;
import static com.github.haseoo.gkproject.imagetransformations.utils.Utils.getResourceURL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainWindow = new FXMLLoader(getResourceURL(MAIN_WINDOW_FXML_PATH));
        Parent root = mainWindow.load();

        Scene scene = new Scene(root);

        stage.setTitle(APPLICATION_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
