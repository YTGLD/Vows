package com.ytgld.vows.event;

import com.google.common.collect.Multimap;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.items.Sacrificial;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.PlayerDataHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.client.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VowsEvent {
    @SubscribeEvent
    public void Tick(EntityTickEvent.Pre event){
        if (event.getEntity() instanceof LivingEntity livingEntity) {
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
                Multimap<Holder<Attribute>, AttributeModifier> attributes = baseVows.doAttribute(player,baseVows);
                if (!attributes.isEmpty()) {
                    attributes.values().removeIf(modifier -> skipped.isSkipped(modifier.id()));
                    evt.addTooltipLines(Component.empty());

                    attributesTooltip.add(Component.translatable("vows.vows.attribute").
                            withStyle(ChatFormatting.GOLD));

                    AttributeUtil.applyTextFor(
                            stack,
                            attributesTooltip::add,
                            attributes,
                            AttributeTooltipContext.of(player, context, context.flag()));
                    baseVows.applyText(baseVows.getDefaultInstance(),attributesTooltip,evt.getContext().flag());
                    for (Component component : attributesTooltip) {
                        MutableComponent co = component.copy();
                        evt.addTooltipLines(co);
                    }
                }
            }
        }
    }
}
