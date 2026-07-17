package com.ytgld.vows.vowsitems;

import com.ytgld.vows.Vows;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class DeathString extends BaseVows {
    public DeathString(Properties properties) {
        super(properties);
    }
    public static final String attackDamage = "VowsDeathString";
    public static void damage(LivingDamageEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("death_string"))) {
                LivingEntity livingEntity = event.getEntity();
                if (livingEntity.getHealth() >= livingEntity.getMaxHealth()) {
                    event.setAmount(event.getAmount() * 0.1f);
                }
                CompoundTag compoundTag = player.getPersistentData();
                if (compoundTag.getFloat(attackDamage) < 0.2f){
                    compoundTag.putFloat(attackDamage,compoundTag.getFloat(attackDamage) + 0.02f);
                }
                float damageBonus = compoundTag.getFloat(attackDamage);
                if (damageBonus > 0) {
                    event.setAmount(event.getAmount() * 1 + damageBonus);
                }
            }
        }
    }

    @Override
    public void tickVows(LivingEntity entity) {
        super.tickVows(entity);
        if (entity instanceof Player player) {
            CompoundTag compoundTag = player.getPersistentData();
            if (compoundTag.getFloat(attackDamage) > 0){
                if (player.tickCount % 200 == 1) {
                    compoundTag.putFloat(attackDamage, compoundTag.getFloat(attackDamage) - 0.02f);
                }
            }
        }
    }

    @Override
    public String itemName() {
        return Handler.mixinName("death_string");

    }

    @Override
    public List<ColorAndImage> colorAndImage() {
        return List.of(
                new ColorAndImage(Light.ARGB.color(255,200,20,20),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png")),
                new ColorAndImage(Light.ARGB.color(255,200,50,100),
                        Vows.fromNamespaceAndPath("textures/soul/soul_4.png")),
                new ColorAndImage(Light.ARGB.color(255,255,50,100),
                        Vows.fromNamespaceAndPath("textures/soul/soul_2.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.death_string.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.death_string.2"),false);
    }
    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.death_string");
    }

}
