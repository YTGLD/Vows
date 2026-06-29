package com.ytgld.vows.items.vows.shield;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.RecipePlugin;
import com.ytgld.vows.tool.RegisterRecipeConfig;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
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

public class SoulLife extends BaseVows {
    public SoulLife(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("soul_life");
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,50,80,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,100,175,255),
                        Vows.fromNamespaceAndPath("textures/soul/soul_20.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.soul_life.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.soul_life.2"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.soul_life.3"),false);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity, item);

        float stronger = 0;
        int maxShield = 20;

        if (livingEntity instanceof Player player) {
            AttributeInstance instance = player.getAttribute(VowsAttributes.soulShieldMaxValue);
            if (instance != null) {
                int max = (int) instance.getValue();
                if (player.getData(VowsAttributes.soulShield) <= max * 0.25f) {
                    stronger = 1;
                }
            }
        }

        modifierMultimap.put(VowsAttributes.soulShieldMaxValue,
                new AttributeModifier(Vows.fromNamespaceAndPath("soul_life"),
                        maxShield, AttributeModifier.Operation.ADD_VALUE)) ;

        modifierMultimap.put(VowsAttributes.soulShieldStronger,
                new AttributeModifier(Vows.fromNamespaceAndPath("soul_life"),
                        stronger, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;

        modifierMultimap.put(Attributes.MAX_HEALTH,
                new AttributeModifier(Vows.fromNamespaceAndPath("soul_life"),
                        -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;



        return modifierMultimap;
    }
    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.soul_life");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.SHIELD.getDefaultInstance(),
                    Items.HEART_OF_THE_SEA.getDefaultInstance(),
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
            return Handler.mixinName("soul_life");
        }
    }

}
