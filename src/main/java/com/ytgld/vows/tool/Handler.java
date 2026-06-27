package com.ytgld.vows.tool;

import com.ytgld.vows.Vows;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Handler {
    public static boolean has(Player player,String itemName){
        Set<String> strings = player.getData(PlayerDataHandler.vVowsSet);
        return strings.contains(itemName);
    }
    public static Set<Item> getVowsItems(Player player){
        Set<String> strings = player.getData(PlayerDataHandler.vVowsSet);
        Set<Item> set = new HashSet<>();
        if (!strings.isEmpty()) {
            for (String name : strings){
                Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(name));
                set.add(item);
            }
        }
        return set;
    }
    public static void addVows(Player player,String itemName){
        Set<String> strings = player.getData(PlayerDataHandler.vVowsSet);
        strings.add(itemName);
    }
    public static Item getVowsItemForName(String itemName){
        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName));
    }
    public static String mixinName(String s){
        return Vows.MODID + ":" + s;
    }
}
