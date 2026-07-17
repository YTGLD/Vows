package com.ytgld.vows.tool;

import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.capability.ModCapabilities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

public class Handler {
    public static boolean has(LivingEntity player,String itemName){
        Set<String> strings = ModCapabilities.getVows(player);
        return strings.contains(itemName);
    }
    public static boolean has(LivingEntity player,Item itemName){
        Set<Item> strings = getVowsItems(player);
        return strings.contains(itemName);
    }
    public static Set<Item> getVowsItems(LivingEntity player){
        Set<Item> set = new HashSet<>();
        if (player!=null) {
            Set<String> strings = ModCapabilities.getVows(player);
            if (!strings.isEmpty()) {
                for (String name : strings) {
                    Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(name));
                    set.add(item);
                }
            }
        }
        return set;
    }
    public static float doValue(float value, LivingEntity entity){
        return value;
    }

    public static void addSoulShield(LivingEntity living , int number){
        if (living instanceof Player player) {
            AttributeInstance attribute = player.getAttribute(VowsAttributes.soulShieldMaxValue.get());
            AttributeInstance attributeCooldown = player.getAttribute(VowsAttributes.soulShieldHealCooldown.get());

            if (attribute != null && attributeCooldown != null) {
                int swordIntent = ModCapabilities.getoulShieldCapability(player);
                int soulValue = (int) attribute.getValue();
                if (swordIntent >= soulValue) {
                    return;
                }
                int newValue = swordIntent + number;
                if (newValue > soulValue) {
                    newValue = soulValue;
                }

                ModCapabilities.setSoulShieldCapability(player,newValue);
            }
        }
    }

    public static int getData(Player player){
        return ModCapabilities.getoulShieldCapability(player);
    }

    public static void addVows(Player player, String itemName){
        player.getCapability(ModCapabilities.theIVowsCapability).ifPresent(oldCap -> {
            var valueSwordStronger= oldCap.getValueSwordStronger();
            valueSwordStronger.add(itemName);
            oldCap.setValueSwordStronger(valueSwordStronger);
        });
    }

    public static int getMaxVows(Player player){
        return (int) player.getAttributeValue(VowsAttributes.maxVows.get());
    }
    public static Item getVowsItemForName(String itemName){
        return BuiltInRegistries.ITEM.get(new ResourceLocation(itemName));
    }
    public static String mixinName(String s){
        return Vows.MODID + ":" + s;
    }
}
