package com.ytgld.vows.block;

import com.ytgld.vows.Vows;
import com.ytgld.vows.block.base.VowsBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VowsBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Vows.MODID);

    public static final Supplier<BlockEntityType<VowsBlockEntity>> VowsBlockEntity_ = REGISTER.register(
            "vows_block_entity",
            // The block entity type.
            () -> new BlockEntityType<>(
                    VowsBlockEntity::new,
                    false,
                    VowsBlocks.VowsBlock_.get()
            )
    );
}
