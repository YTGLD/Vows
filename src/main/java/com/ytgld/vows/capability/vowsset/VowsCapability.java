package com.ytgld.vows.capability.vowsset;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;
public class VowsCapability implements IVowsCapability, INBTSerializable<CompoundTag> {

    private Set<String> value = new HashSet<>();

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        ListTag list = new ListTag();

        for (String s : value) {
            list.add(StringTag.valueOf(s));
        }

        tag.put("vows", list);

        return tag;
    }


    @Override
    public void deserializeNBT(CompoundTag nbt) {

        value.clear();

        ListTag list = nbt.getList("vows", Tag.TAG_STRING);

        for (int i = 0; i < list.size(); i++) {
            value.add(list.getString(i));
        }
    }


    @Override
    public void setValueSwordStronger(Set<String> value) {
        this.value = value;
    }
    @Override
    public Set<String> getValueSwordStronger() {
        return value;
    }
}