package com.ytgld.vows.capability.soulshield.cap;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class SoulShieldCapability implements ISwordCapability, INBTSerializable<CompoundTag> {

    private int value = 0;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("value_sword_stronger", value);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.value = nbt.getInt("value_sword_stronger");
    }

    @Override
    public void setValueSwordStronger(int value) {
        this.value = value;

    }

    @Override
    public int getValueSwordStronger() {
        return value;
    }
}