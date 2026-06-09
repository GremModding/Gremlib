package io.gremstudio.gremlib.block.util;

import io.gremstudio.gremlib.mixin.WoodTypeMixin;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * A simple util class for WoodTypes, takes care of creation and registration with a single method. Also contains shorthand for vanilla's variants.
 */
public class WoodTypeUtil {
    public static WoodType of(String name, BlockSetType setType, SoundType soundType, SoundType hangingSignSoundType, SoundEvent fenceGateClose, SoundEvent fenceGateOpen) {
        WoodType woodType = new WoodType(name, setType, soundType, hangingSignSoundType, fenceGateClose, fenceGateOpen);
        return WoodTypeMixin.registerWoodType(woodType);
    }

    public static WoodType of(String name, BlockSetType setType) {
        return of(name, setType, SoundType.WOOD, SoundType.HANGING_SIGN, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
    }

    public static WoodType ofNether(String name, BlockSetType setType) {
        return of(name, setType, SoundType.NETHER_WOOD, SoundType.NETHER_WOOD_HANGING_SIGN, SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN);
    }

    public static WoodType ofBamboo(String name, BlockSetType setType) {
        return of(name, setType, SoundType.BAMBOO_WOOD, SoundType.BAMBOO_WOOD_HANGING_SIGN, SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE, SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN);
    }

    public static WoodType ofCherry(String name, BlockSetType setType) {
        return of(name, setType,SoundType.CHERRY_WOOD, SoundType.CHERRY_WOOD_HANGING_SIGN, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN);
    }

    public static WoodType fromCopy(String name, BlockSetType setType, WoodType baseType) {
        return of(name, setType, baseType.soundType(), baseType.hangingSignSoundType(), baseType.fenceGateClose(), baseType.fenceGateOpen());
    }

    public static WoodType fromCopy(String name, WoodType baseType) {
        return fromCopy(name, baseType.setType(), baseType);
    }
}
