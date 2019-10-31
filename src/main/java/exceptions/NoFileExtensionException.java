package exceptions;

import java.io.IOException;

import static exceptions.ExceptionMessages.NO_FILE_EXTENSION;

public class NoFileExtensionException extends IOException {
    public NoFileExtensionException() {
        super(NO_FILE_EXTENSION);
    }
}
