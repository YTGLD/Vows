package com.ytgld.vows.items.vows.war;

import com.google.common.collect.Multimap;
import com.ytgld.vows.Vows;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.RecipePlugin;
import com.ytgld.vows.tool.RegisterRecipeConfig;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
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

import java.util.List;
import java.util.Set;

public class BreakingFate extends BaseVows {
    public BreakingFate(Properties properties) {
        super(properties);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("breaking_fate");
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(LivingEntity livingEntity, Item item) {
        Multimap<Holder<Attribute>, AttributeModifier> modifierMultimap = super.doAttribute(livingEntity, item);

        float attribute = 0;

        if (livingEntity instanceof Player player) {
            float maxHealth = player.getMaxHealth();
            float health = player.getHealth();

            float doIt = 1 - (health / Math.max(1,maxHealth));
            attribute = maxAttribute(player) * doIt;
        }

        modifierMultimap.put(Attributes.ARMOR,
                new AttributeModifier(Vows.fromNamespaceAndPath("breaking_fate"),
                        attribute, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;

        modifierMultimap.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(Vows.fromNamespaceAndPath("breaking_fate"),
                        attribute, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;

        modifierMultimap.put(Attributes.MOVEMENT_SPEED,
                new AttributeModifier(Vows.fromNamespaceAndPath("breaking_fate"),
                        attribute, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;

        modifierMultimap.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(Vows.fromNamespaceAndPath("breaking_fate"),
                        attribute / 2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)) ;


        return modifierMultimap;
    }
    public static void hurt(LivingDamageEvent.Pre event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("breaking_fate"))) {
                event.setNewDamage(event.getNewDamage() * (1 + doHurt(player)));
            }
        }
    }

    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.breaking_fate.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.breaking_fate.2"),false);
    }

    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,200,20,20),
                        Vows.fromNamespaceAndPath("textures/soul/soul_16.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,255,50,100),
                        Vows.fromNamespaceAndPath("textures/soul/soul_17.png"))
        );
    }
    public static float maxAttribute(LivingEntity livingEntity){
        return Handler.doValue(0.2f,livingEntity);
    }

    public static float doHurt(LivingEntity livingEntity){
        return Handler.doValue(0.2f,livingEntity);
    }
    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.breaking_fate");
    }

    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.REDSTONE.getDefaultInstance(),
                    Items.ENCHANTED_BOOK.getDefaultInstance(),
                    Items.ROTTEN_FLESH.getDefaultInstance()
            );
        }
        @Override
        public boolean canRecipe(Set<Item> items) {
            return items.contains(this.itemList().get(0).getItem())
                    && items.contains(this.itemList().get(1).getItem())
                    && items.contains(this.itemList().get(2).getItem());
        }

        @Override
        public String output() {
            return Handler.mixinName("breaking_fate");
        }
    }

}
