package com.ytgld.vows.tool;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeHandler {
    public static boolean canUse(List<ItemStack> itemList, Set<ItemStack> items){
        boolean[] booleans = new boolean[itemList.size()];
        int index = 0;
        for (ItemStack stack : items){
            if (stack.is(itemList.get(index).getItem())
                    && stack.count() == itemList.get(index).count()){
                booleans[index] = true;
            }
            if (index < itemList.size() - 1) {
                index++;
            }
        }
        List<Integer> integers = new ArrayList<>();
        for (boolean boo : booleans){
            if (!boo) {
                integers.add(1);
            }
        }
        return integers.isEmpty();
    }
}
