package io.gremstudio.gremlib.mod.submod;

import io.gremstudio.gremlib.mod.GremMod;
import net.minecraft.resources.Identifier;

// Submods! I have more plans for it but as you can see clearly its not much in there rn.
// Submods should be able to do the exact things normal mods can do.
public abstract class SubGremMod {
    protected final GremMod parent;

    public SubGremMod(GremMod parent) {
        this.parent = parent;
    }

    public GremMod getParent() {
        return parent;
    }

    public abstract String getSubmodID();

    public Identifier createID(String path) {
        return Identifier.fromNamespaceAndPath(parent.getModID(), getSubmodID() + "/" + path);
    }
}
