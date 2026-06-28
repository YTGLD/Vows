package com.ytgld.vows.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public abstract class BaseVows extends Item {
    public BaseVows(Properties properties) {
        super(properties.stacksTo(1));
    }
    public abstract String itemName();
    public abstract List<RenderVowsItem.ColorAndImage> colorAndImage();
    public abstract Component textMain();

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (Handler.getVowsItems(player).contains(player.getItemInHand(usedHand).getItem())) {
            player.sendOverlayMessage(Component.translatable("vows.vows.has").withStyle(Style.EMPTY.withColor(0xffff0000)));
            return super.use(level, player, usedHand);
        }else {
            if (Handler.getVowsItems(player).size() < 3) {
                Handler.addVows(player, itemName());
                stack.shrink(1);
            } else {
                player.sendOverlayMessage(Component.translatable("vows.vows.has").withStyle(Style.EMPTY.withColor(0xffff0000)));
            }
        }
        return super.use(level, player, usedHand);
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        MutableComponent soul =  Component
                .translatable("vows.vows")
                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(Light.ARGB.color(255, 120, 90, 180))));
        co.setStyle(Style.EMPTY.withColor(Light.ARGB.color(255, 210, 75, 210)));

        return soul.append(Component.literal("<").withStyle(ChatFormatting.GRAY))
                .append(co)
                .append(Component.literal(">").withStyle(ChatFormatting.GRAY));
    }

    public void tickVows(LivingEntity entity){

    }

    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity,Item item){
        return HashMultimap.create();
    }

    public void applyText(ItemStack stack,List<Component> tooltipComponents,TooltipFlag tooltipFlag){}

    public void addText(List<Component> list,MutableComponent component,boolean positive){
        ChatFormatting chatFormatting = ChatFormatting.BLUE;
        String at = "+";
        if (!positive) {
            chatFormatting = ChatFormatting.RED;
            at = "-";
        }
        list.add(Component.literal(at).withStyle(chatFormatting).append(component.withStyle(chatFormatting)));
    }

    @Override
    public final void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        builder.accept(textMain().copy().withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
