package io.gremstudio.gremlib.multiloader;

import io.gremstudio.gremlib.multiloader.block.LoaderBlockAPI;

import java.nio.file.Path;

/**
 * A set of small util stuff for Loaders.
 * <br> Might need to be expanded over time, who knows.
 *
 */

public interface Loader {
    boolean isDevMode();
    boolean isClient();
    String getLoader();
    Path getGameDir();
    boolean isModPresent(String mod);
    boolean isDataGenerating();

    LoaderBlockAPI blocks();

}
