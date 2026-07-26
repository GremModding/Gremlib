package io.gremstudio.gremlib.multiloader;

public class MixinImplementationError extends IllegalAccessError {
    public MixinImplementationError(String message) {
        super(message);
    }
}
