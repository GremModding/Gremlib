package io.gremstudio.gauntlet.util;

import java.util.List;

public class RunUtil {
    public static List<String> additionalJVMArgs() {
        return List.of(
                "-XX:+IgnoreUnrecognizedVMOptions",
                "-XX:+AllowEnhancedClassRedefinition"
        );
    }
}
