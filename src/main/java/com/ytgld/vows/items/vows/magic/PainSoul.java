package com.ytgld.vows.items.vows.magic;

import com.google.common.collect.HashMultimap;
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
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
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Set;

public class PainSoul extends BaseVows {
    public PainSoul(Properties properties) {
        super(properties);
    }
    @Override
    public void tickVows(LivingEntity entity) {
        super.tickVows(entity);
        if (entity instanceof Player player) {
            AttributeInstance instance = player.getAttributes().getInstance(Attributes.MAX_HEALTH);
            if (instance != null) {
                for (AttributeModifier modifier : instance.getModifiers()){
                    player.getAttributes().addTransientAttributeModifiers(modifierMultimap(modifier));
                }
            }
        }
    }
    private Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap (AttributeModifier modifier){
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap = HashMultimap.create();
        modifierMultimap.put(VowsAttributes.soulShieldMaxValue, new AttributeModifier(
                Vows.fromNamespaceAndPath(modifier.id().getNamespace() + modifier.id().getPath() + "pain_soul"),
                modifier.amount(),modifier.operation()));
        return modifierMultimap;
    }


    public static int addShieldDouble(Player player, int value){
        if (Handler.has(player, Handler.mixinName("pain_soul"))) {
            return value * 2;
        }
        return value;
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity,item);

        modifierMultimap.put(Attributes.MAX_HEALTH,new AttributeModifier(
                Vows.fromNamespaceAndPath("pain_soul"),
                -0.6F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        modifierMultimap.put(VowsAttributes.soulShieldMaxValue,new AttributeModifier(
                Vows.fromNamespaceAndPath("pain_soul"),
                4, AttributeModifier.Operation.ADD_VALUE));

        return modifierMultimap;
    }

    @Override
    public String itemName() {
        return Handler.mixinName("pain_soul");
    }
    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,235,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_20.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,180,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_19.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.pain_soul.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.pain_soul.2"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.pain_soul.3"),false);
        addText(tooltipComponents,Component.translatable("vows.vows.pain_soul.4"),false);
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.pain_soul");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.NETHERITE_SCRAP.getDefaultInstance(),
                    Items.ECHO_SHARD.getDefaultInstance(),
                    Items.ENCHANTED_BOOK.getDefaultInstance()
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
            return Handler.mixinName("pain_soul");
        }
    }

}



