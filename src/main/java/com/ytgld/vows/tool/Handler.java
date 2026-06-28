package com.ytgld.vows.tool;

import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.VowsAttributes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class Handler {
    public static boolean has(LivingEntity player,String itemName){
        Set<String> strings = player.getData(PlayerDataHandler.vVowsSet);
        return strings.contains(itemName);
    }
    public static boolean has(LivingEntity player,Item itemName){
        Set<Item> strings = getVowsItems(player);
        return strings.contains(itemName);
    }
    public static Set<Item> getVowsItems(LivingEntity player){
        Set<String> strings = player.getData(PlayerDataHandler.vVowsSet);
        Set<Item> set = new HashSet<>();
        if (!strings.isEmpty()) {
            for (String name : strings){
                Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(name));
                set.add(item);
            }
        }
        return set;
    }
    public static float doValue(float value, LivingEntity entity){
        return value;
    }

    public static void addSoulShield(LivingEntity living , int number){
        if (living instanceof Player player) {
            AttributeInstance attribute = player.getAttribute(VowsAttributes.soulShieldMaxValue);
            AttributeInstance attributeCooldown = player.getAttribute(VowsAttributes.soulShieldHealCooldown);
            if (attribute != null && attributeCooldown != null) {
                int cooldown = player.getData(VowsAttributes.soulShieldCooldown);
                if (cooldown > 0) {
                    number = 0;
                }
                int soulValue = (int) attribute.getValue();
                int data = player.getData(VowsAttributes.soulShield);
                if (data >= soulValue) {
                    return;
                }
                int newValue = data + number;
                if (newValue > soulValue) {
                    newValue = soulValue;
                }

                setDataValue(player,VowsAttributes.soulShield,newValue);
            }
        }
    }
    public static <T> void setDataValue(Player player,Supplier<AttachmentType<T>> type,T value) {
        player.setData(type,value);
    }
    public static <T> T getData(Supplier<AttachmentType<T>> type,Player player) {
        return player.getData(type);
    }
    public static void addVows(Player player,String itemName){
        Set<String> strings = player.getData(PlayerDataHandler.vVowsSet);
        strings.add(itemName);
    }
    public static Item getVowsItemForName(String itemName){
        return BuiltInRegistries.ITEM.getValue(Identifier.parse(itemName));
    }
    public static String mixinName(String s){
        return Vows.MODID + ":" + s;
    }
}
