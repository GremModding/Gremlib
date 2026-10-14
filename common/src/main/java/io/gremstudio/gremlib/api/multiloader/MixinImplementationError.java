package io.gremstudio.gremlib.api.multiloader;

public class MixinImplementationError extends IllegalAccessError {
    public MixinImplementationError(String message) {
        super(message);
    }
}
