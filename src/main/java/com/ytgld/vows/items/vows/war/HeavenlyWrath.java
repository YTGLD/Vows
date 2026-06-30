package com.ytgld.vows.items.vows.war;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Set;

public class HeavenlyWrath extends BaseVows {
    public HeavenlyWrath(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("heavenly_wrath");
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity, item);

        float damageAttackSpeed = 0;
        float armor = 0;

        if (livingEntity instanceof Player player) {
            if (player.getHealth() <= player.getMaxHealth() * 0.3f) {
                damageAttackSpeed = 0.33f;
                armor = -0.5f;
            }
        }
        modifierMultimap.put(Attributes.ARMOR,
                new AttributeModifier(Vows.fromNamespaceAndPath("heavenly_wrath"),
                        armor, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;
        modifierMultimap.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(Vows.fromNamespaceAndPath("heavenly_wrath"),
                        damageAttackSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)) ;
        modifierMultimap.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(Vows.fromNamespaceAndPath("heavenly_wrath"),
                        damageAttackSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)) ;
        return modifierMultimap;
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,200,20,20),
                        Vows.fromNamespaceAndPath("textures/soul/soul_1.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,120,30,0),
                        Vows.fromNamespaceAndPath("textures/soul/soul_2.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,180,0,50),
                        Vows.fromNamespaceAndPath("textures/soul/soul_5.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,180,0,50),
                        Vows.fromNamespaceAndPath("textures/soul/soul_11.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.heavenly_wrath.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.heavenly_wrath.2"),false);
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.heavenly_wrath");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.DIAMOND_CHESTPLATE.getDefaultInstance(),
                    new ItemStack(Items.SPIDER_EYE,8)
            );
        }
        @Override
        public boolean canRecipe(Set<ItemStack> items) {
            return RecipeHandler.canUse(itemList(),items);
        }
        @Override
        public String output() {
            return Handler.mixinName("heavenly_wrath");
        }
    }
}
