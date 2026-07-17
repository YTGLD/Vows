package com.ytgld.vows.vowsitems;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.capability.ModCapabilities;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
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
import java.util.UUID;

public class SoulLife extends BaseVows {
    public SoulLife(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("soul_life");
    }

    @Override
    public List<ColorAndImage> colorAndImage() {
        return List.of(
                new ColorAndImage(Light.ARGB.color(255,50,80,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png")),
                new ColorAndImage(Light.ARGB.color(255,100,175,255),
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
    public Multimap<Attribute, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Attribute, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity, item);

        float stronger = 0;
        int maxShield = 20;

        if (livingEntity instanceof Player player) {
            AttributeInstance instance = player.getAttribute(VowsAttributes.soulShieldMaxValue.get());
            if (instance != null) {
                int max = (int) instance.getValue();
                if (ModCapabilities.getoulShieldCapability(player) <= max * 0.25f) {
                    stronger = 1;
                }
            }
        }

        modifierMultimap.put(VowsAttributes.soulShieldMaxValue.get(),
                new AttributeModifier(UUID.fromString("5fdc5e50-9630-44e3-9ca3-c3dc50c901d9"),this.getDescriptionId(),
                        maxShield, AttributeModifier.Operation.ADDITION)) ;

        modifierMultimap.put(VowsAttributes.soulShieldStronger.get(),
                new AttributeModifier(UUID.fromString("5fdc5e50-9630-44e3-9ca3-c3dc50c901d9"),this.getDescriptionId(),
                        stronger, AttributeModifier.Operation.MULTIPLY_TOTAL)) ;

        modifierMultimap.put(Attributes.MAX_HEALTH,
                new AttributeModifier(UUID.fromString("5fdc5e50-9630-44e3-9ca3-c3dc50c901d9"),this.getDescriptionId(),
                        -0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL)) ;



        return modifierMultimap;
    }
    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.soul_life");
    }


}
