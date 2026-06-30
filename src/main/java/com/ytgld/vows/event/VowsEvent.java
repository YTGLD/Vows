package com.ytgld.vows.event;

import com.google.common.collect.HashMultimap;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.items.vows.hunger.ChecksBalances;
import com.ytgld.vows.items.vows.hunger.HungryWolf;
import com.ytgld.vows.items.vows.hunger.Sacrificial;
import com.ytgld.vows.items.vows.magic.CtrlMagic;
import com.ytgld.vows.items.vows.magic.SoulDrive;
import com.ytgld.vows.items.vows.shield.BoneEmperor;
import com.ytgld.vows.items.vows.shield.BreakShield;
import com.ytgld.vows.items.vows.shield.Judge;
import com.ytgld.vows.items.vows.war.*;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.PlayerDataHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VowsEvent {
    @SubscribeEvent
    public void Tick(EntityTickEvent.Pre event){
        SoulShieldHealHandler.tick(event);
        if (event.getEntity() instanceof Player livingEntity) {
            Set<String> set = livingEntity.getData(PlayerDataHandler.vVowsSet);
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
        Sacrificial.eat(event);
    }
    @SubscribeEvent
    public void StartUseItemEvent(LivingEntityUseItemEvent.Start event){
        HungryWolf.speed(event);
    }
    @SubscribeEvent
    public void LivingUseTotemEvent(LivingUseTotemEvent event){
        BreakShield.hurt(event);
    }
    @SubscribeEvent
    public void LivingDamageEventPre(LivingDamageEvent.Pre event) {
        BoneEmperor.hurt(event);

        SoulShieldHealHandler.hurt(event);
        BreakingFate.hurt(event);
        WarFortress.damage(event);
        DeathString.damage(event);
        Rage.damage(event);
        Judge.hurtBreak(event);
        BreakShield.hurt(event);
        CtrlMagic.hurt(event);
        SoulDrive.hurt(event);
    }
    @SubscribeEvent
    public void LivingDamageEventPost(LivingDamageEvent.Post event) {
        BreakSword.healLife(event);
    }
    @SubscribeEvent
    public void AttackEntityEvent(AttackEntityEvent event) {
        CtrlMagic.hurt(event);
    }
    @SubscribeEvent
    public void LivingDamageEventPost(LivingHealEvent event) {
        BreakSword.doHeal(event);
    }
    @SubscribeEvent
    public void LivingDamageEventPre(LivingIncomingDamageEvent event) {
        ChecksBalances.UmmDamage(event);
    }



    @SubscribeEvent
    public void AddAttributeTooltipsEvent(ItemTooltipEvent event){
        Player player = event.getEntity();
        Item item = event.getItemStack().getItem();

        if (Handler.has(player, item)) {
            if (!event.getFlags().hasShiftDown()){
                event.getToolTip().clear();
            }
            event.getToolTip().add(Component.translatable("vows.vows.has").withStyle(Style.EMPTY.withColor(0xffff0000)));
            event.getToolTip().add(Component.translatable("key.keyboard.left.shift").withStyle(Style.EMPTY.withColor(0xffff0000)));
        }
    }
    @SubscribeEvent
    public void AddAttributeTooltipsEvent(AddAttributeTooltipsEvent evt){
        AttributeTooltipContext context = evt.getContext();
        ItemStack stack = evt.getStack();
        GatherSkippedAttributeTooltipsEvent skipped =
                NeoForge.EVENT_BUS.post(new GatherSkippedAttributeTooltipsEvent(stack, context));

        if (skipped.isSkippingAll()) {
            return;
        }
        List<Component> attributesTooltip = new ArrayList<>();
        Player player = context.player();
        if (player!=null) {
            if (stack.getItem() instanceof BaseVows baseVows) {
                evt.addTooltipLines(Component.empty());
                attributesTooltip.add(Component.translatable("vows.vows.attribute").
                        withStyle(ChatFormatting.GOLD));
                AttributeUtil.applyTextFor(
                        stack,
                        attributesTooltip::add,
                        HashMultimap.create(),
                        AttributeTooltipContext.of(player, context,context.tooltipDisplay(), context.flag()));
                baseVows.applyText(baseVows.getDefaultInstance(),attributesTooltip,evt.getContext().flag());
                for (Component component : attributesTooltip) {
                    MutableComponent co = component.copy();
                    evt.addTooltipLines(co);
                }
            }
        }
    }
}
