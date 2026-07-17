package com.ytgld.vows.vowsitems;

import com.ytgld.vows.Vows;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Set;

public class WarFortress extends BaseVows {
    public WarFortress(Properties properties) {
        super(properties);
    }
    public static void damage(LivingDamageEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("war_fortress"))) {
                LivingEntity  livingEntity = event.getEntity();
                if (!livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0))) {
                    event.setAmount(event.getAmount() * 1.15f);
                }else {
                    event.setAmount(event.getAmount() * 0.8f);
                }
            }
        }
    }
    @Override
    public String itemName() {
        return Handler.mixinName("war_fortress");
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.war_fortress.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.war_fortress.2"),false);
    }
    @Override
    public List<ColorAndImage> colorAndImage() {
        return List.of(
                new ColorAndImage(Light.ARGB.color(255,200,20,20),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png")),
                new ColorAndImage(Light.ARGB.color(255,255,50,100),
                        Vows.fromNamespaceAndPath("textures/soul/soul_3.png"))
        );
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.war_fortress");
    }

}
