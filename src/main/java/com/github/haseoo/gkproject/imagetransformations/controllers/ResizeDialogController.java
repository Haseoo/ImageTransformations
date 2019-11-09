package com.github.haseoo.gkproject.imagetransformations.controllers;

import com.github.haseoo.gkproject.imagetransformations.utils.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.*;
import static javafx.scene.control.Alert.AlertType.ERROR;

public class ResizeDialogController {
    @FXML
    private TextField xRatio;
    @FXML
    private TextField yRatio;

    private Pair<Integer> ratio;

    @FXML
    void initialize() {

        makeTextFieldNumeric(yRatio);
        makeTextFieldNumeric(xRatio);
    }

    @FXML
    void onApply() {
        try {
            ratio = new Pair<>(Integer.parseInt(xRatio.getText()),
                    Integer.parseInt(yRatio.getText()));
            ((Stage) xRatio.getScene().getWindow()).close();
            if (invalidRatio())  {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(ERROR, INVALID_INPUT_ALERT_TEXT);
            alert.showAndWait();
        }
    }

    Optional<Pair> getScaleRatio() {
        return Optional.ofNullable(ratio);
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
