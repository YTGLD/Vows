package com.ytgld.vows.tool;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;

public interface RegisterRecipeConfig {
    List<ItemStack> itemList();
    boolean canRecipe(Set<ItemStack> items);
    String output();
}