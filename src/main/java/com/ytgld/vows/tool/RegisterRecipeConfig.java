package com.ytgld.vows.tool;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;

public interface RegisterRecipeConfig {
    boolean canRecipe(Set<Item> items);
    String output();
}