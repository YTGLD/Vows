package com.ytgld.vows.event;

import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.capability.ModCapabilities;
import com.ytgld.vows.tool.Handler;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SoulShieldHealHandler {
    public static void tick(LivingEvent.LivingTickEvent event){
        if (event.getEntity() instanceof Player living) {
            AttributeInstance max = living.getAttribute(VowsAttributes.soulShieldMaxValue.get());
            AttributeInstance speed = living.getAttribute(VowsAttributes.soulShieldHealSpeed.get());
            if (max != null && speed != null) {
                float time = (float) (15 * speed.getValue());
                if (time < 1) {
                    time = 1;
                }
                float data = Handler.getData(living);
                if (living.tickCount % (time * 7) == 1) {
                    Handler.addSoulShield(living, 1);
                }
                if (data < 0) {
                    ModCapabilities.setSoulShieldCapability(living,0);
                }
            }
        }
    }
    public static void hurt(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player living) {
            AttributeInstance stronger = living.getAttribute(VowsAttributes.soulShieldStronger.get());
            if (stronger != null) {
                float value = (float) stronger.getValue();
                int data = Handler.getData(living);
                if (data > 0) {
                    float damage = event.getAmount();
                    int newData = data - 1 - ((int) (damage * 0.5f));
                    ModCapabilities.setSoulShieldCapability(living,newData);
                    float modify = (float) Math.sqrt(value);
                    if (modify < 0.3f) {
                        modify = 0.3f;
                    }
                    float newDamage = damage * (0.3f / modify);
                    if (!allDamage(living,event.getSource())) {
                        event.setAmount(newDamage);
                    }else {
                        event.setAmount(0);
                    }
                } else if (data < 0){
                    ModCapabilities.setSoulShieldCapability(living,0);
                    AttributeInstance time = living.getAttribute(VowsAttributes.soulShieldHealCooldown.get());
                }
            }
        }
    }
    private static boolean allDamage(Player player, DamageSource source){
        if (source.is(DamageTypeTags.WITCH_RESISTANT_TO)
                || source.is(DamageTypes.MAGIC)) {
            if (Handler.has(player, Handler.mixinName("stronger_shield"))) {
                return true;
            }
        }
        return false;
    }
}