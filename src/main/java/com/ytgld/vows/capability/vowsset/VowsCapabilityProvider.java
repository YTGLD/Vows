package com.ytgld.vows.capability.vowsset;
import com.ytgld.vows.capability.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VowsCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private final VowsCapability backend = new VowsCapability();

    private final LazyOptional<IVowsCapability> optional =
            LazyOptional.of(() -> backend);


    @Override
    public <T> LazyOptional<T> getCapability(
            Capability<T> cap,
            Direction side
    ) {
        return cap == ModCapabilities.theIVowsCapability
                ? optional.cast()
                : LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        return backend.serializeNBT();
    }


    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.deserializeNBT(nbt);
    }
}