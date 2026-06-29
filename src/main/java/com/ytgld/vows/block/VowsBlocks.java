package com.ytgld.vows.block;

import com.ytgld.vows.Vows;
import com.ytgld.vows.block.base.VowsBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VowsBlocks {

    public static final DeferredRegister<Block> REGISTER =
            DeferredRegister.create(Registries.BLOCK, Vows.MODID);

    public static final DeferredHolder<Block,Block> VowsBlock_ =
            REGISTER.register("vows_block", (registryName) -> new VowsBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK,registryName))));
}
