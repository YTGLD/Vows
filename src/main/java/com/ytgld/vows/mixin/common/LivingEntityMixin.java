package com.ytgld.vows.mixin.common;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.PlayerDataHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private Map<Item, Multimap<Holder<Attribute>, AttributeModifier>> vows$AttributeModifier = new HashMap<>();

    @Unique
    private void vows$updateAttribute() {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        Set<String> set = livingEntity.getData(PlayerDataHandler.vVowsSet);
        for (String name : set){
            Item item = Handler.getVowsItemForName(name);
            if (item instanceof BaseVows baseVows) {
                Multimap<Holder<Attribute>, AttributeModifier> doAttribute = baseVows.doAttribute(livingEntity,item);
                vows$AttributeModifier.getOrDefault(item, HashMultimap.create()).forEach((attributeHolder, attributeModifier)->{
                    Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
                    modifiers.put(attributeHolder,attributeModifier);
                    livingEntity.getAttributes().removeAttributeModifiers(modifiers);
                });
                livingEntity.getAttributes().addTransientAttributeModifiers(doAttribute);

                vows$AttributeModifier.put(item, doAttribute);
            }
        }
    }
    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void tick(CallbackInfo ci) {
        vows$updateAttribute();
    }
}
