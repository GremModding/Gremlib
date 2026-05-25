package io.siuolplex.gremlib.block.util;

import io.siuolplex.gremlib.mixin.BlockSetTypeMixin;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class BlockSetTypeUtil {
    public static BlockSetType of(String name,
                           boolean canOpenByHand, boolean canOpenByWindCharge, boolean canButtonBeActivatedByArrows,
                           BlockSetType.PressurePlateSensitivity pressurePlateSensitivity, SoundType soundType,
                           SoundEvent doorClose, SoundEvent doorOpen,
                           SoundEvent trapdoorClose, SoundEvent trapdoorOpen,
                           SoundEvent pressurePlateClickOff, SoundEvent pressurePlateClickOn,
                           SoundEvent buttonClickOff, SoundEvent buttonClickOn) {
        BlockSetType type = new BlockSetType(name, canOpenByHand, canOpenByWindCharge, canButtonBeActivatedByArrows,
                pressurePlateSensitivity, soundType, doorClose, doorOpen, trapdoorClose, trapdoorOpen,
                pressurePlateClickOff, pressurePlateClickOn, buttonClickOff, buttonClickOn);

        return BlockSetTypeMixin.registerBlockSetType(type);
    }

    public static BlockSetType ofWood(String name,
                               SoundType soundType,
                               SoundEvent doorClose,
                               SoundEvent doorOpen,
                               SoundEvent trapdoorClose,
                               SoundEvent trapdoorOpen,
                               SoundEvent pressurePlateClickOff,
                               SoundEvent pressurePlateClickOn,
                               SoundEvent buttonClickOff,
                               SoundEvent buttonClickOn) {
        return of(name, true, true, true,
                BlockSetType.PressurePlateSensitivity.EVERYTHING, soundType, doorClose, doorOpen,
                trapdoorClose, trapdoorOpen, pressurePlateClickOff, pressurePlateClickOn, buttonClickOff, buttonClickOn);
    }

    public static BlockSetType ofWood(String name) {
        return ofWood(name, SoundType.WOOD, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN,
                SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN,
                SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
    }

    public static BlockSetType ofNetherWood(String name) {
        return ofWood(name, SoundType.NETHER_WOOD, SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundEvents.NETHER_WOOD_DOOR_OPEN,
                SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE, SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN,
                SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON);
    }

    public static BlockSetType ofBambooWood(String name) {
        return ofWood(name, SoundType.BAMBOO_WOOD, SoundEvents.BAMBOO_WOOD_DOOR_CLOSE, SoundEvents.BAMBOO_WOOD_DOOR_OPEN,
                SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE, SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN,
                SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON);
    }

    public static BlockSetType ofCherryWood(String name) {
        return ofWood(name, SoundType.CHERRY_WOOD, SoundEvents.CHERRY_WOOD_DOOR_CLOSE, SoundEvents.CHERRY_WOOD_DOOR_OPEN,
                SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE, SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN,
                SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON);
    }

    public static BlockSetType ofStone(String name) {
        return of(name,true, true, false,
                BlockSetType.PressurePlateSensitivity.MOBS, SoundType.STONE,
                SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN,
                SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, // Would this or the one above even be needed? Eh.
                SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON);
    }

    public static BlockSetType fromCopy(String name, BlockSetType baseType) {
        return of(name, baseType.canOpenByHand(), baseType.canOpenByWindCharge(), baseType.canButtonBeActivatedByArrows(),
                baseType.pressurePlateSensitivity(), baseType.soundType(), baseType.doorClose(), baseType.doorOpen(), baseType.trapdoorClose(), baseType.trapdoorOpen(),
                baseType.pressurePlateClickOff(), baseType.pressurePlateClickOn(), baseType.buttonClickOff(), baseType.buttonClickOn()  );
    }
}
