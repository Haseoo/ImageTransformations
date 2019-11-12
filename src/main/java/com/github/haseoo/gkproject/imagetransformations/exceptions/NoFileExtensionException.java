package com.github.haseoo.gkproject.imagetransformations.exceptions;

import java.io.IOException;

import static com.github.haseoo.gkproject.imagetransformations.exceptions.ExceptionMessages.NO_FILE_EXTENSION;

public class NoFileExtensionException extends IOException {
    public NoFileExtensionException() {
        super(NO_FILE_EXTENSION);
    }
}
