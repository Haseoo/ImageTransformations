package com.github.haseoo.gkproject.imagetransformations.utils;

import com.github.haseoo.gkproject.imagetransformations.Main;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

import static com.github.haseoo.gkproject.imagetransformations.utils.Constants.EXTENSION_SEPARATOR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    public static URL getResourceURL(String relativePath) {
        return Objects.requireNonNull(Main.class.getClassLoader().getResource(relativePath));
    }

    public static String[] getOpenFileExtensions() {
        return new String[]{"*.png", "*.jpg", "*.jpeg", "*.bmp"};
    }

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains(EXTENSION_SEPARATOR))
                .map(f -> f.substring(filename.lastIndexOf(EXTENSION_SEPARATOR) + 1));
    }
}
