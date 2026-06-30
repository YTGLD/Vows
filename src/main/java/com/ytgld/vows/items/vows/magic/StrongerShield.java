package com.ytgld.vows.items.vows.magic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.other.VowsDamageTypes;
import com.ytgld.vows.tool.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Set;

public class StrongerShield extends BaseVows {
    public StrongerShield(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity,item);

        modifierMultimap.put(VowsAttributes.soulShieldMaxValue,new AttributeModifier(
                Vows.fromNamespaceAndPath("stronger_shield"),
                8, AttributeModifier.Operation.ADD_VALUE));

        modifierMultimap.put(VowsAttributes.soulShieldHealCooldown,new AttributeModifier(
                Vows.fromNamespaceAndPath("stronger_shield"),
                1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifierMultimap;
    }
    @Override
    public String itemName() {
        return Handler.mixinName("stronger_shield");
    }
    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,235,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_20.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,235,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_7.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,180,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.stronger_shield.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.stronger_shield.2"),false);
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.stronger_shield");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.DIAMOND_HORSE_ARMOR.getDefaultInstance(),
                    new ItemStack(Items.ECHO_SHARD,4),
                    Items.HONEY_BOTTLE.getDefaultInstance()
            );
        }
        @Override
        public boolean canRecipe(Set<ItemStack> items) {
            return RecipeHandler.canUse(itemList(),items);
        }
        @Override
        public String output() {
            return Handler.mixinName("stronger_shield");
        }
    }

}




