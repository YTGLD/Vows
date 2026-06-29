package com.ytgld.vows.items.vows.war;

import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.RecipePlugin;
import com.ytgld.vows.tool.RegisterRecipeConfig;
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

public class WarFortress extends BaseVows {
    public WarFortress(Properties properties) {
        super(properties);
    }
    public static void damage(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("war_fortress"))) {
                LivingEntity  livingEntity = event.getEntity();
                if (!livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0))) {
                    event.setNewDamage(event.getNewDamage() * 1.15f);
                }else {
                    event.setNewDamage(event.getNewDamage() * 0.8f);
                }
            }
        }
    }
    @Override
    public String itemName() {
        return Handler.mixinName("war_fortress");
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.war_fortress.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.war_fortress.2"),false);
    }
    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,200,20,20),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,255,50,100),
                        Vows.fromNamespaceAndPath("textures/soul/soul_3.png"))
        );
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.war_fortress");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.BLAZE_ROD.getDefaultInstance(),
                    Items.FERMENTED_SPIDER_EYE.getDefaultInstance(),
                    Items.COPPER_INGOT.getDefaultInstance()
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
            return Handler.mixinName("war_fortress");
        }
    }

}
