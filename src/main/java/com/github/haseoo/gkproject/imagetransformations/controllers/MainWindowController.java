package com.github.haseoo.gkproject.imagetransformations.controllers;

import com.github.haseoo.gkproject.imagetransformations.enums.FileDialogOperation;
import com.github.haseoo.gkproject.imagetransformations.utils.ImageResize;
import com.github.haseoo.gkproject.imagetransformations.utils.ImageRotation;
import com.github.haseoo.gkproject.imagetransformations.utils.JavaFXUtils;
import exceptions.NoFileExtensionException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.*;
import static com.github.haseoo.gkproject.imagetransformations.utils.JavaFXUtils.copyImage;
import static com.github.haseoo.gkproject.imagetransformations.utils.Utils.getExtensionByStringHandling;
import static com.github.haseoo.gkproject.imagetransformations.utils.Utils.getFileExtensions;

@Slf4j
public class MainWindowController {
    @FXML
    private MenuItem saveImage;
    @FXML
    private MenuItem restoreOriginal;
    @FXML
    private Menu editMenu;
    @FXML
    private ImageView imageView;

    private Image currentImage;
    private Image rotatableImage;
    private Image originalImage;
    private double angle;

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
    void onResize() throws IOException {
        JavaFXUtils.<ResizeDialogController>displayInputDialog(RESIZE_DIALOG_FXML_PATH)
                .getScaleRatio()
                .ifPresent(ratio -> {
                    currentImage = ImageResize.simpleResize(currentImage, ratio);
                    rotatableImage = ImageResize.simpleResize(rotatableImage, ratio);
                    imageView.setImage(currentImage);
                });
    }

    @FXML
    void onRotate() throws IOException {
        JavaFXUtils.<RotateDialogController>displayInputDialog(ROTATE_DIALOG_FXML_PATH)
                .getRotation()
                .ifPresent(rotation -> {
                    angle += rotation;
                    currentImage = ImageRotation.rotateImage(rotatableImage, angle);
                    imageView.setImage(currentImage);
                });
    }

    @FXML
    void onRestore() {
        currentImage = originalImage;
        imageView.setImage(currentImage);
        angle = 0.0;
        rotatableImage = copyImage(originalImage);
    }

    private void readImage(File image) {
        String imageUri = image.toURI().toString();
        log.info(String.format(FILE_OPEN_STRING_FORMAT, imageUri));
        currentImage = new Image(imageUri);
        originalImage = new Image(imageUri);
        rotatableImage = new Image(imageUri);
        imageView.setImage(currentImage);
        saveImage.setDisable(false);
        restoreOriginal.setDisable(false);
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
