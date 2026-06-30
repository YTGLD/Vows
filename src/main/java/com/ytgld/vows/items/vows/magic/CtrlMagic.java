package com.ytgld.vows.items.vows.magic;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.other.VowsDamageTypes;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.RecipePlugin;
import com.ytgld.vows.tool.RegisterRecipeConfig;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;

public class CtrlMagic extends BaseVows {
    public CtrlMagic(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("ctrl_magic");
    }


    public static void hurt(LivingDamageEvent.Pre event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("ctrl_magic"))) {
                if (event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)
                        || event.getSource().is(VowsDamageTypes.thePlayerMagic)
                        || event.getSource().is(DamageTypes.MAGIC)
                ) {
                    event.setNewDamage(event.getNewDamage() * 1.6f);
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("ctrl_magic"))) {
                if (event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)
                        || event.getSource().is(VowsDamageTypes.thePlayerMagic)
                        || event.getSource().is(DamageTypes.MAGIC) && !(event.getSource().getEntity() instanceof Player player1 && !player1.is(player))) {
                    event.setNewDamage(event.getNewDamage() * 1.2f);
                }
            }
        }
    }
    public static void hurt(AttackEntityEvent event){
        Player player = event.getEntity();
        if (event.getTarget() instanceof LivingEntity livingEntity) {
            if (Handler.has(player, Handler.mixinName("ctrl_magic"))) {
                livingEntity.hurt(VowsDamageTypes.playerMagic(player),
                        (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2f));
            }
        }
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.ctrl_magic.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.ctrl_magic.2"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.ctrl_magic.3"),false);
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,235,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_8.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,180,125,235),
                        Vows.fromNamespaceAndPath("textures/soul/soul_7.png"))
        );
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.ctrl_magic");
    }

    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.ECHO_SHARD.getDefaultInstance(),
                    Items.SLIME_BALL.getDefaultInstance(),
                    Items.HONEY_BOTTLE.getDefaultInstance(),
                    Items.NETHER_WART.getDefaultInstance()
            );
        }
        @Override
        public boolean canRecipe(Set<Item> items) {
            return items.contains(this.itemList().get(0).getItem())
                    && items.contains(this.itemList().get(1).getItem())
                    && items.contains(this.itemList().get(2).getItem())
                    && items.contains(this.itemList().get(3).getItem());
        }

        @Override
        public String output() {
            return Handler.mixinName("ctrl_magic");
        }
    }

}

