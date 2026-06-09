package io.gremstudio.gremlib.fabric.initializers;

/**
 * The GremMod initializer...
 * <p>
 * I need it so I can be sure that the gremmods will set everything up in a way that can be easily captured for registration and the alike.
 *
 */
public interface GremModInitializer {
    public String ENTRYPOINT_ID = "gremlib:common";

    void onGremModInitalization();
}
