package com.ytgld.vows.event;

import com.ytgld.vows.VowsClient;
import com.ytgld.vows.capability.ModCapabilities;
import com.ytgld.vows.client.RenderSoulShield;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.client.gui_particles.BlackParticlesAdd;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.vowsitems.ChecksBalances;
import com.ytgld.vows.vowsitems.BoneEmperor;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.vowsitems.BreakSword;
import com.ytgld.vows.vowsitems.DeathString;
import com.ytgld.vows.vowsitems.WarFortress;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ytgld.vows.attributre.VowsAttributes.*;

public class VowsEvent {

    @SubscribeEvent
    public void Tick(LivingEvent.LivingTickEvent event){
        SoulShieldHealHandler.tick(event);
        if (event.getEntity() instanceof Player livingEntity) {
            Set<String> set = ModCapabilities.getVows(livingEntity);
            for (String name : set){
                Item item = Handler.getVowsItemForName(name);
                if (item instanceof BaseVows baseVows) {
                    baseVows.tickVows(livingEntity);
                }
            }
        }
    }
    @SubscribeEvent
    public void FinishUseItemEvent(LivingEntityUseItemEvent.Finish event){
    }
    @SubscribeEvent
    public void StartUseItemEvent(LivingEntityUseItemEvent.Start event){
    }
    @SubscribeEvent
    public void LivingUseTotemEvent(LivingUseTotemEvent event) {
    }
    @SubscribeEvent
    public void LivingDamageEventPre(LivingDamageEvent event) {
        BoneEmperor.hurt(event);

        SoulShieldHealHandler.hurt(event);
        WarFortress.damage(event);
        DeathString.damage(event);
    }
    @SubscribeEvent
    public void LivingDamageEventPost(LivingDamageEvent event) {
        BreakSword.healLife(event);
    }
    @SubscribeEvent
    public void AttackEntityEvent(AttackEntityEvent event) {
    }
    @SubscribeEvent
    public void LivingDamageEventPost(LivingHealEvent event) {
        BreakSword.doHeal(event);

    }
    @SubscribeEvent
    public void LivingDamageEventPre(LivingHurtEvent event) {
        ChecksBalances.UmmDamage(event);
    }



    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void EntityRenderersEvent(RenderTooltipEvent.Color event) {
        if (event.getItemStack().getItem() instanceof BaseVows baseVows) {
            event.setBorderEnd(Light.ARGB.color(255,255,100,255));
            event.setBorderStart(Light.ARGB.color(255,255,100,255));
        }
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void EntityRenderersEvent(TickEvent.ClientTickEvent event) {
        VowsClient.time++;
        BlackParticlesAdd.tick();
        RenderVowsItem.clientTick(event);
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void EntityRenderersEvent(RenderGuiEvent.Post event) {
        RenderSoulShield.render(event);
    }

}
