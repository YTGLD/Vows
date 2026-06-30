package com.ytgld.vows.items.vows.life;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.other.VowsDamageTypes;
import com.ytgld.vows.tool.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

import java.util.List;
import java.util.Set;

public class Hyperplasia extends BaseVows {
    public Hyperplasia(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("hyperplasia");
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = super.doAttribute(livingEntity, item);
        multimap.put(Attributes.MAX_HEALTH,new AttributeModifier(Vows.fromNamespaceAndPath("hyperplasia"),
                0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        multimap.put(Attributes.MOVEMENT_SPEED,new AttributeModifier(Vows.fromNamespaceAndPath("hyperplasia"),
                -0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return multimap;
    }
    public static void heal120(LivingHealEvent event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("hyperplasia"))) {
                event.setAmount(event.getAmount() * 1.2f);
            }
        }
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.hyperplasia.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.hyperplasia.2"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.hyperplasia.3"),false);
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,240,100,160),
                        Vows.fromNamespaceAndPath("textures/soul/soul_23.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,255,100,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_24.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,255,80,140),
                        Vows.fromNamespaceAndPath("textures/soul/soul_25.png"))
        );
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.hyperplasia");
    }

    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    new ItemStack(Items.GLISTERING_MELON_SLICE,8),
                    new ItemStack(Items.GHAST_TEAR,4),
                    new ItemStack(Items.NETHER_WART,32),
                    new ItemStack(Items.BEEF,16),
                    new ItemStack(Items.ROTTEN_FLESH,32)
            );
        }
        @Override
        public boolean canRecipe(Set<ItemStack> items) {
            return RecipeHandler.canUse(itemList(),items);
        }

        @Override
        public String output() {
            return Handler.mixinName("hyperplasia");
        }
    }

}


