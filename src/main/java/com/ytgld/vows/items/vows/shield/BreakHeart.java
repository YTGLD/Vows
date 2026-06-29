package com.ytgld.vows.items.vows.shield;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.RecipePlugin;
import com.ytgld.vows.tool.RegisterRecipeConfig;
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

public class BreakHeart extends BaseVows {
    public BreakHeart(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("break_heart");
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,50,80,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_14.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,100,175,255),
                        Vows.fromNamespaceAndPath("textures/soul/soul_22.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.break_heart.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.break_heart.2"),false);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity, item);


        modifierMultimap.put(Attributes.ARMOR,
                new AttributeModifier(Vows.fromNamespaceAndPath("break_heart"),
                        0.9F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;
        modifierMultimap.put(Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(Vows.fromNamespaceAndPath("break_heart"),
                        0.9F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;

        modifierMultimap.put(Attributes.MAX_HEALTH,
                new AttributeModifier(Vows.fromNamespaceAndPath("break_heart"),
                        -0.3F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;
        return modifierMultimap;
    }
    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.break_heart");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.MELON_SLICE.getDefaultInstance(),
                    Items.GHAST_TEAR.getDefaultInstance(),
                    Items.IRON_INGOT.getDefaultInstance(),
                    Items.NETHER_WART.getDefaultInstance()
            );
        }
        @Override
        public boolean canRecipe(Set<Item> items) {
            return items.contains(this.itemList().get(0).getItem())
                    && items.contains(this.itemList().get(1).getItem())
                    && items.contains(this.itemList().get(2).getItem())
                    && items.contains(this.itemList().get(3).getItem());
        }

        @Override
        public String output() {
            return Handler.mixinName("break_heart");
        }
    }

}


