package com.ytgld.vows.tool;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;

public interface RegisterRecipeConfig {
    List<ItemStack> itemList();
    default ItemStack doOffItem(){
        return Items.NETHER_STAR.asItem().getDefaultInstance();
    }
    boolean canRecipe(Set<ItemStack> items);
    String output();
}