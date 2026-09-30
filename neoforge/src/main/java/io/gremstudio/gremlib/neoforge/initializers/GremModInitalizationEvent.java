package io.gremstudio.gremlib.neoforge.initializers;

import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

/**
 * In order to have confidence that various Gremlib events fire at the correct time, there's a special initalization event for Neoforge!
 * <br>
 * It fires during {@link net.neoforged.fml.event.lifecycle.FMLConstructModEvent}, specifically when Gremlib gets called.
 * <br> It is not recommended to try and initialize your stuff outside the GremModInitialization event.
 *
 * <br>
 * <br>
 * See also: Fabric's GremModInitializer.
 */
public class GremModInitalizationEvent extends Event implements IModBusEvent {
}
