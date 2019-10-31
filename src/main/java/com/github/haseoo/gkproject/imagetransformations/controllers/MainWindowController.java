package com.github.haseoo.gkproject.imagetransformations.controllers;

import com.github.haseoo.gkproject.imagetransformations.enums.FileDialogOperation;
import exceptions.NoFileExtensionException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.*;
import static com.github.haseoo.gkproject.imagetransformations.utils.Utils.getExtensionByStringHandling;
import static com.github.haseoo.gkproject.imagetransformations.utils.Utils.getFileExtensions;

@Slf4j
public class MainWindowController {
    @FXML
    private MenuItem saveImage;
    @FXML
    private Menu editMenu;
    @FXML
    private ImageView imageView;

    private Image currentImage;

    @FXML
    public void onOpen() {
        File image = getFileFromDialog(FileDialogOperation.OPEN);
        if (image != null) {
            readImage(image);
        }
    }

    @FXML
    void onSave() throws IOException {
        File dest = getFileFromDialog(FileDialogOperation.SAVE);
        if (dest != null) {
            saveImage(dest);
        }
    }

    @FXML
    public void onTest() { /*TO JEST TYLKO TEST*/
        WritableImage newImage = new WritableImage((int) currentImage.getWidth(), (int) currentImage.getHeight());
        val oldPixels = currentImage.getPixelReader();
        val newPixels = newImage.getPixelWriter();
        for (int x = 0; x < currentImage.getWidth(); x++) {
            for (int y = 0; y < currentImage.getHeight(); y++) {
                Color oldColor = oldPixels.getColor(x, y);
                double avg = (oldColor.getRed() + oldColor.getBlue() + oldColor.getGreen()) / 3.0;
                Color newColor = new Color(avg, avg, avg, oldColor.getOpacity());
                newPixels.setColor(x, y, newColor);
            }
        }
        currentImage = newImage;
        imageView.setImage(currentImage);
    }

    private void readImage(File image) {
        String imageUri = image.toURI().toString();
        log.info(String.format(FILE_OPEN_STRING_FORMAT, imageUri));
        currentImage = new Image(imageUri);
        imageView.setImage(currentImage);
        saveImage.setDisable(false);
        editMenu.setDisable(false);
    }

    private void saveImage(File dest) throws IOException {
        String extension = getExtensionByStringHandling(dest.getName()).orElseThrow(NoFileExtensionException::new);
        BufferedImage bImage = SwingFXUtils.fromFXImage(currentImage, null);
        log.info(String.format(FILE_SAVE_STRING_FORMAT, dest.getPath(), extension));
        ImageIO.write(bImage, extension, dest);
    }

    private File getFileFromDialog(FileDialogOperation operation) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(FILE_FILTER_NAME, getFileExtensions()));
        switch (operation) {
            case OPEN:
                return fileChooser.showOpenDialog(imageView.getScene().getWindow());
            case SAVE:
                return fileChooser.showSaveDialog(imageView.getScene().getWindow());
            default:
                throw new UnsupportedOperationException();
        }
    }
}
