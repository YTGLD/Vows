package com.ytgld.vows.items.vows.shield;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Set;

public class Judge extends BaseVows {
    public Judge(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("judge");
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,50,80,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_7.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,100,175,255),
                        Vows.fromNamespaceAndPath("textures/soul/soul_5.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,120,180,255),
                        Vows.fromNamespaceAndPath("textures/soul/soul_1.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.judge.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.judge.2"),false);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity, item);
        modifierMultimap.put(Attributes.KNOCKBACK_RESISTANCE,
                new AttributeModifier(Vows.fromNamespaceAndPath("judge"),
                        0.8f, AttributeModifier.Operation.ADD_VALUE)) ;
        return modifierMultimap;
    }
    public static void hurtBreak(LivingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("judge"))) {
                doBreak(player,EquipmentSlot.HEAD);
                doBreak(player,EquipmentSlot.CHEST);
                doBreak(player,EquipmentSlot.LEGS);
                doBreak(player,EquipmentSlot.FEET);
            }
        }
    }
    private static void doBreak(Player player,EquipmentSlot slot){
        player.getItemBySlot(slot).hurtAndBreak(5,player,slot);
    }
    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.judge");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.HEAVY_CORE.getDefaultInstance(),
                    new ItemStack(Items.FERMENTED_SPIDER_EYE,2),
                    Items.STONE_AXE.getDefaultInstance(),
                    new ItemStack(Items.IRON_INGOT,8),
                    Items.BLAZE_POWDER.getDefaultInstance()
            );
        }
        @Override
        public boolean canRecipe(Set<ItemStack> items) {
            return RecipeHandler.canUse(itemList(),items);
        }
        @Override
        public String output() {
            return Handler.mixinName("judge");
        }
    }

}

