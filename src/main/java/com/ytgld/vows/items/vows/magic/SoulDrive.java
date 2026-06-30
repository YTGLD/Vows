package com.ytgld.vows.items.vows.magic;

import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.other.VowsDamageTypes;
import com.ytgld.vows.tool.*;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

import java.util.List;
import java.util.Set;

public class SoulDrive extends BaseVows {
    public SoulDrive(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("soul_drive");
    }


    public static void hurt(LivingDamageEvent.Pre event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("soul_drive"))) {
                if (event.getSource().is(VowsDamageTypes.thePlayerMagic)
                        || event.getSource().is(DamageTypes.MAGIC)
                        || event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)
                ) {
                    event.setNewDamage(event.getNewDamage() * 0.3F);
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("soul_drive"))) {
                if (event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)
                        || event.getSource().is(DamageTypes.MAGIC)
                        || event.getSource().is(VowsDamageTypes.thePlayerMagic)) {
                    event.setNewDamage(event.getNewDamage() * 0.5F);
                }
            }
        }
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.soul_drive.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.soul_drive.2"),false);
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,235,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_1.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,180,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_5.png"))
        );
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.soul_drive");
    }

    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    new ItemStack(Items.ECHO_SHARD,2),
                    new ItemStack(Items.GLOWSTONE_DUST,16),
                    new ItemStack(Items.IRON_INGOT,8),
                    Items.SHIELD.getDefaultInstance()
            );
        }
        @Override
        public boolean canRecipe(Set<ItemStack> items) {
            return RecipeHandler.canUse(itemList(),items);
        }

        @Override
        public String output() {
            return Handler.mixinName("soul_drive");
        }
    }
}


