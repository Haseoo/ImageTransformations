package exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages extends RuntimeException {
    public static final String NO_FILE_EXTENSION = "Blank file extension";
}
