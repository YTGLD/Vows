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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FamineConstraints extends BaseVows {
    public FamineConstraints(Properties properties) {
        super(properties);
    }
    @Override
    public String itemName() {
        return Handler.mixinName("famine_constraints");
    }
    @Override
    public Multimap<Attribute, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Attribute, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity, item);

        float speedAndDamage = 0;

        if (livingEntity instanceof Player player) {
            FoodData foodData=  player.getFoodData();
            if (foodData.getFoodLevel() > 14) {
                speedAndDamage = getDamageAndSpeed(livingEntity);
            }else if (foodData.getFoodLevel() < 10){
                speedAndDamage = getWeakness(livingEntity);
            }
        }

        modifierMultimap.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(UUID.fromString("85efa8a5-a24d-4418-a86e-1125907fed62"),this.getDescriptionId(),
                        speedAndDamage, AttributeModifier.Operation.MULTIPLY_TOTAL)) ;
        modifierMultimap.put(Attributes.MOVEMENT_SPEED,
                new AttributeModifier(UUID.fromString("85efa8a5-a24d-4418-a86e-1125907fed62"),this.getDescriptionId(),
                        speedAndDamage, AttributeModifier.Operation.MULTIPLY_TOTAL)) ;


        return modifierMultimap;
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.famine_constraints");
    }

    @Override
    public List<ColorAndImage> colorAndImage() {
        return List.of(
                new ColorAndImage(Light.ARGB.color(255,20,255,20),
                        Vows.fromNamespaceAndPath("textures/soul/soul_1.png")),
                new ColorAndImage(Light.ARGB.color(255,0,220,50),
                        Vows.fromNamespaceAndPath("textures/soul/soul_3.png")),
                new ColorAndImage(Light.ARGB.color(255,50,200,0),
                        Vows.fromNamespaceAndPath("textures/soul/soul_4.png")),
                new ColorAndImage(Light.ARGB.color(255,100,180,100),
                        Vows.fromNamespaceAndPath("textures/soul/soul_7.png")),
                new ColorAndImage(Light.ARGB.color(255,10,180,10),
                        Vows.fromNamespaceAndPath("textures/soul/soul_9.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.famine_constraints.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.famine_constraints.2"),false);
    }

    public float getDamageAndSpeed (LivingEntity livingEntity){
        return Handler.doValue(0.15f,livingEntity);
    }
    public float getWeakness (LivingEntity livingEntity){
        return Handler.doValue(-0.2f,livingEntity);
    }

}
