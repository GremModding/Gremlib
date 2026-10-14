package io.gremstudio.gremlib.api.mod.submod;

import io.gremstudio.gremlib.api.mod.BaseGremMod;
import net.minecraft.resources.Identifier;

// Submods are going to probably be in a weird limbo state for a while.
// Where they exist and Ill try to design with them in mind, but they wont be explicitly supported in the same way nor will I let it be blocking.
// When I hopefully get around to SIE, I'll see what I can do.
public abstract class SubGremMod extends BaseGremMod {
    protected final BaseGremMod parent;

    public SubGremMod(BaseGremMod parent) {
        super();
        this.parent = parent;
    }

    public BaseGremMod getParent() {
        return parent;
    }

    public abstract String getModID();

    public Identifier createId(String path) {
        return Identifier.fromNamespaceAndPath(parent.getModID(), getModID() + "/" + path);
    }
}
