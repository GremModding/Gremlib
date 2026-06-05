package io.siuolplex.gremlib.neoforge.impl;

import io.siuolplex.gremlib.neoforge.mixin.AxeItemAccessor;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

// No we are NOT going to just use the Datamap when making multiloader mods. Call me back when Fabric has the exact same datamaps as Neo.
public class StrippableHandler {

    // Yeah its unfortunate that you need to make a new map every time but to be fair this one is supposed to be unmodifiable or something?
    public static void addStrippable(Block input, Block output) {
        Map<Block, Block> strippables = new HashMap<>( AxeItemAccessor.getStrippables());
        strippables.put(input, output);
        AxeItemAccessor.setStrippables(strippables);
    }
}
