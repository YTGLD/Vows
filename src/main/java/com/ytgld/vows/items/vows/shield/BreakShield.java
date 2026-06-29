package com.ytgld.vows.items.vows.shield;

import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.RecipePlugin;
import com.ytgld.vows.tool.RegisterRecipeConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;

import java.util.List;
import java.util.Set;

public class BreakShield extends BaseVows {
    public BreakShield(Properties properties) {
        super(properties);
    }
    public static void hurt(LivingDamageEvent.Pre event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("break_shield"))) {
                event.setNewDamage(event.getNewDamage() * (0.8f));
            }
        }
    }
    public static void hurt(LivingUseTotemEvent event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("break_shield"))) {
                event.setCanceled(true);
            }
        }
    }
    @Override
    public String itemName() {
        return Handler.mixinName("break_shield");
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,50,80,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_21.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,100,175,255),
                        Vows.fromNamespaceAndPath("textures/soul/soul_22.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.break_shield.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.break_shield.2"),false);
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.break_shield");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.TOTEM_OF_UNDYING.getDefaultInstance(),
                    Items.GOLD_INGOT.getDefaultInstance(),
                    Items.GHAST_TEAR.getDefaultInstance()
            );
        }
        @Override
        public boolean canRecipe(Set<Item> items) {
            return items.contains(this.itemList().get(0).getItem())
                    && items.contains(this.itemList().get(1).getItem())
                    && items.contains(this.itemList().get(2).getItem());
        }

        @Override
        public String output() {
            return Handler.mixinName("break_shield");
        }
    }

}

