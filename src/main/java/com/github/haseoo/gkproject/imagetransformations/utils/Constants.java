package com.github.haseoo.gkproject.imagetransformations.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String APPLICATION_NAME = "Image transformations";
    public static final String MAIN_WINDOW_FXML_PATH = "MainWindow.fxml";
    public static final String RESIZE_DIALOG_FXML_PATH = "ResizeDialog.fxml";
    public static final String ROTATE_DIALOG_FXML_PATH = "RotateDialog.fxml";
    public static final String FILE_OPEN_STRING_FORMAT = "Opening file %s";
    public static final String FILE_SAVE_STRING_FORMAT = "Saving file %s, extension %s";
    public static final String FILE_FILTER_NAME = "Image files";
    public static final String EXTENSION_SEPARATOR = ".";
    public static final int NO_OFFSET = 0;
    public static final String IS_NOT_A_NUMBER_REGEX = "[^\\d]";
    public static final String IS_A_NUMBER_REGEX = "\\d*";
    public static final String IS_A_DECIMAL_REGEX = "\\d*(\\.\\d*)?";
    public static final String REPLACEMENT_STRING = "";
    public static final String INVALID_INPUT_ALERT_TEXT = "Incorrect or empty input";
}
