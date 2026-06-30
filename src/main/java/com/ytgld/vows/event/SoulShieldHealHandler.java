package com.ytgld.vows.event;

import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.other.VowsDamageTypes;
import com.ytgld.vows.tool.Handler;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class SoulShieldHealHandler {
    public static void tick(EntityTickEvent.Pre event){
        if (event.getEntity() instanceof Player living) {
            upDataSoulShield(living);
            int cooldown = living.getData(VowsAttributes.soulShieldCooldown);
            if (cooldown <= 0) {
                AttributeInstance max = living.getAttribute(VowsAttributes.soulShieldMaxValue);
                AttributeInstance speed = living.getAttribute(VowsAttributes.soulShieldHealSpeed);
                if (max != null && speed != null) {
                    float time = (float) (15 * speed.getValue());
                    if (time < 1) {
                        time = 1;
                    }
                    float data = living.getData(VowsAttributes.soulShield);
                    if (living.tickCount % (time * 7) == 1) {
                        Handler.addSoulShield(living, 1);
                    }
                    if (data < 0) {
                        Handler.setDataValue(living,VowsAttributes.soulShield,0);
                    }
                }
            }
        }
    }
    public static void hurt(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player living) {
            int soulShieldCooldown = living.getData(VowsAttributes.soulShieldCooldown);
            if (soulShieldCooldown > 0) {
                return;
            }
            AttributeInstance stronger = living.getAttribute(VowsAttributes.soulShieldStronger);
            if (stronger != null) {
                float value = (float) stronger.getValue();
                int data = living.getData(VowsAttributes.soulShield);
                if (data > 0) {
                    float damage = event.getNewDamage();
                    int newData = data - 1 - ((int) (damage * 0.5f));
                    Handler.setDataValue(living,VowsAttributes.soulShield,newData);
                    float modify = (float) Math.sqrt(value);
                    if (modify < 0.3f) {
                        modify = 0.3f;
                    }
                    float newDamage = damage * (0.3f / modify);
                    if (!allDamage(living,event.getSource())) {
                        event.setNewDamage(newDamage);
                    }else {
                        event.setNewDamage(0);
                    }
                } else {
                    Handler.setDataValue(living,VowsAttributes.soulShield,0);
                    AttributeInstance time = living.getAttribute(VowsAttributes.soulShieldHealCooldown);
                    if (time != null) {
                        int cooldown = (int) time.getValue();
                        Handler.setDataValue(living,VowsAttributes.soulShieldCooldown,cooldown);
                    }
                }
            }
        }
    }
    private static boolean allDamage(Player player, DamageSource source){
        if (source.is(DamageTypeTags.WITCH_RESISTANT_TO)
                || source.is(DamageTypes.MAGIC)
                || source.is(VowsDamageTypes.thePlayerMagic)) {
            if (Handler.has(player, Handler.mixinName("stronger_shield"))) {
                return true;
            }
        }
        return false;
    }
    private static void upDataSoulShield(Player player){
        if (player.tickCount % 20 == 1) {
            int c = player.getData(VowsAttributes.soulShieldCooldown);
            int newV = c - 1;
            if (newV < 0) {
                newV = 0;
            }
            Handler.setDataValue(player,VowsAttributes.soulShieldCooldown, newV);
        }
    }
}