package com.ytgld.vows.items.vows.war;

import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Set;

public class Rage extends BaseVows {
    public Rage(Properties properties) {
        super(properties);
    }
    public static void damage(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("rage"))) {
                if (player.getHealth() >= player.getMaxHealth() * 0.8f) {
                    event.setNewDamage(event.getNewDamage() * 1.3f);
                }
            }
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("rage"))) {
                float maxHealth = player.getMaxHealth();
                float health = player.getHealth();

                float doIt = 1 - (health / Math.max(1,maxHealth));

                event.setNewDamage(event.getNewDamage() * (1 + doIt));
            }
        }
    }
    @Override
    public String itemName() {
        return Handler.mixinName("rage");
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.rage.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.rage.2"),false);
    }
    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,200,20,20),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,255,50,100),
                        Vows.fromNamespaceAndPath("textures/soul/soul_19.png"))
        );
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.rage");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    new ItemStack(Items.BLAZE_POWDER,6),
                    new ItemStack(Items.BONE,24),
                    new ItemStack(Items.NETHERITE_SCRAP,2)
            );
        }
        @Override
        public boolean canRecipe(Set<ItemStack> items) {
            return RecipeHandler.canUse(itemList(),items);
        }
        @Override
        public String output() {
            return Handler.mixinName("rage");
        }
    }

}

