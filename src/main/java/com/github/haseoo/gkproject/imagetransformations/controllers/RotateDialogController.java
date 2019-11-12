package com.github.haseoo.gkproject.imagetransformations.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;

import java.util.Optional;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.INVALID_INPUT_ALERT_TEXT;
import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.IS_A_DECIMAL_REGEX;
import static javafx.scene.control.Alert.AlertType.ERROR;

public class RotateDialogController {
    @FXML
    private TextField input;
    @FXML
    private Toggle rightToggle;

    private Double alpha;

    @FXML
    void initialize() {
        makeTextFieldDecimal(input);
    }

    @FXML
    void onApply() {
        try {
            alpha = Math.toRadians(Double.parseDouble(input.getText()));
            if (rightToggle.isSelected()) {
                alpha = -alpha;
            }
            ((Stage) input.getScene().getWindow()).close();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(ERROR, INVALID_INPUT_ALERT_TEXT);
            alert.showAndWait();
        }
    }

    @FXML
    void onCancel() {
        ((Stage) input.getScene().getWindow()).close();
    }

    public Optional<Double> getRotation() {
        return Optional.ofNullable(alpha);
    }

    private static void makeTextFieldDecimal(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(IS_A_DECIMAL_REGEX)) {
                textField.setText(oldValue);
            }
        });
    }
}
