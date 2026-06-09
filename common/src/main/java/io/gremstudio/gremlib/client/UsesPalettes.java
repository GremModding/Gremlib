package io.gremstudio.gremlib.client;

/** This interface acts as a check to see if an object should be redirected to use Atlases instead of regular textures.
 * <p>It is specifically made for things like Boats or Sign-gui where it would be needed. It does require implementation through mixin usually.
 */
public interface UsesPalettes {}
