package io.gremstudio.gremlib.impl.mixin;

import io.gremstudio.gremlib.api.world.interaction.event.item.ItemWorldInteractions;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemLike, FeatureElement, ItemWorldInteractions {
}
