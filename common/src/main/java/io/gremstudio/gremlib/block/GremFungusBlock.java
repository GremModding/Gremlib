package io.gremstudio.gremlib.block;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherFungusBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GremFungusBlock extends NetherFungusBlock {
    public GremFungusBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, Block requiredBlock, TagKey<Block> supportBlocks, Properties properties) {
        super(feature, requiredBlock, supportBlocks, properties);
    }
}
