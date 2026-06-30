package com.ytgld.vows.items.vows.war;

import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

import java.util.List;
import java.util.Set;

public class BreakSword extends BaseVows {
    public BreakSword(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("break_sword");
    }
    public static void healLife(LivingDamageEvent.Post event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("break_sword"))) {
                player.heal(event.getHealthDamage() * life(player));
            }
        }
    }
    public static float life(LivingEntity entity){
        return Handler.doValue(0.1f,entity);
    }

    public static void doHeal(LivingHealEvent event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("break_sword"))) {
                event.setAmount(event.getAmount() * (1 - heal(player)));
            }
        }
    }
    public static float heal(LivingEntity entity){
        return Handler.doValue(0.3f,entity);
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,200,20,20),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,255,50,100),
                        Vows.fromNamespaceAndPath("textures/soul/soul_18.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.break_sword.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.break_sword.2"),false);
    }
    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.break_sword");
    }

    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.MUSIC_DISC_TEARS.getDefaultInstance(),
                    new ItemStack(Items.DIAMOND,8)
            );
        }
        @Override
        public boolean canRecipe(Set<ItemStack> items) {
            return RecipeHandler.canUse(itemList(),items);
        }
        @Override
        public String output() {
            return Handler.mixinName("break_sword");
        }
    }
}
