package io.gremstudio.gremlib.multiloader;

import io.gremstudio.gremlib.multiloader.block.BlockAPI;

import java.nio.file.Path;

/**
 * Loader, as the name suggests, is a
 * <br> Might need to be expanded over time, who knows.
 */

public interface Loader {
    boolean isDevMode();
    boolean isClient();
    String getLoader();
    Path getGameDir();
    boolean isModPresent(String mod);
    boolean isDataGenerating();
}
