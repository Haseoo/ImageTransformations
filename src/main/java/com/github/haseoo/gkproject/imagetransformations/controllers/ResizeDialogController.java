package com.github.haseoo.gkproject.imagetransformations.controllers;

import com.github.haseoo.gkproject.imagetransformations.utils.ImageResize;
import com.github.haseoo.gkproject.imagetransformations.utils.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.*;
import static javafx.scene.control.Alert.AlertType.ERROR;

public class ResizeDialogController {
    @FXML
    private TextField xRatio;
    @FXML
    private TextField yRatio;
    @FXML
    private ToggleGroup resizeAlgorithm;
    @FXML
    private ToggleButton bilinearRadio;
    @FXML
    private ToggleButton simpleResizeRadio;

    private Pair<Integer> ratio;
    private Map<Toggle, BiFunction<Image, Pair<Integer>, Image>> resizeAlgorithmMap;

    @FXML
    void initialize() {
        resizeAlgorithmMap = new HashMap<>();
        resizeAlgorithmMap.put(bilinearRadio, ImageResize::bilinearResize);
        resizeAlgorithmMap.put(simpleResizeRadio, ImageResize::simpleResize);
        makeTextFieldNumeric(yRatio);
        makeTextFieldNumeric(xRatio);
    }

    @FXML
    void onApply() {
        try {
            ratio = new Pair<>(Integer.parseInt(xRatio.getText()),
                    Integer.parseInt(yRatio.getText()));
            ((Stage) xRatio.getScene().getWindow()).close();
            if (invalidRatio()) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(ERROR, INVALID_INPUT_ALERT_TEXT);
            alert.showAndWait();
        }
    }

    @FXML
    void onCancel() {
        ((Stage) xRatio.getScene().getWindow()).close();
    }

    Optional<Pair<Integer>> getScaleRatio() {
        return Optional.ofNullable(ratio);
    }

    Optional<BiFunction<Image, Pair<Integer>, Image>> getAlgorithm() {
        return Optional.ofNullable(resizeAlgorithmMap.get(resizeAlgorithm.getSelectedToggle()));
    }

    private boolean invalidRatio() {
        return ratio.getX() <= 0 || ratio.getY() <= 0;
    }

    private static void makeTextFieldNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(IS_A_NUMBER_REGEX)) {
                textField.setText(newValue.replaceAll(IS_NOT_A_NUMBER_REGEX, REPLACEMENT_STRING));
            }
        });
    }
}
