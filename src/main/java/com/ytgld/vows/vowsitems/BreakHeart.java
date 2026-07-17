package com.ytgld.vows.vowsitems;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BreakHeart extends BaseVows {
    public BreakHeart(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("break_heart");
    }

    @Override
    public List<ColorAndImage> colorAndImage() {
        return List.of(
                new ColorAndImage(Light.ARGB.color(255,50,80,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_14.png")),
                new ColorAndImage(Light.ARGB.color(255,100,175,255),
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
    public Multimap<Attribute, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Attribute, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity, item);


        modifierMultimap.put(Attributes.ARMOR,
                new AttributeModifier(UUID.fromString("000a02c0-37b5-49eb-b29a-1e5be67e8d5b"),this.getDescriptionId(),
                        0.9F, AttributeModifier.Operation.MULTIPLY_TOTAL)) ;
        modifierMultimap.put(Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(UUID.fromString("000a02c0-37b5-49eb-b29a-1e5be67e8d5b"),this.getDescriptionId(),
                        0.9F, AttributeModifier.Operation.MULTIPLY_TOTAL)) ;

        modifierMultimap.put(Attributes.MAX_HEALTH,
                new AttributeModifier(UUID.fromString("000a02c0-37b5-49eb-b29a-1e5be67e8d5b"),this.getDescriptionId(),
                        -0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL)) ;
        return modifierMultimap;
    }
    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.break_heart");
    }


}


