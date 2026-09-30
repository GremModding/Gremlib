package io.gremstudio.gremlib.fabric.initializers;

/**
 * In order to have confidence that various Gremlib events fire at the correct time, there's a special initializer for Fabric!
 * <br>
 * It fires during {@link net.fabricmc.api.ModInitializer}, specifically when Gremlib gets initalized.
 * <br> It is not recommended to try and initialize your stuff outside the GremModInitializer.
 *
 * <br>
 * <br>
 * See also: Neoforge's GremModInitalizationEvent.
 */
public interface GremModInitializer {
    String ENTRYPOINT_ID = "gremlib:common";

    void onGremModInitalization();
}
