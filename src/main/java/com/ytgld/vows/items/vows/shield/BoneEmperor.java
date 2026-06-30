package com.ytgld.vows.items.vows.shield;

import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.RecipePlugin;
import com.ytgld.vows.tool.RegisterRecipeConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Set;

public class BoneEmperor extends BaseVows {
    private static final String soulBoneEmperor = "soulBoneEmperor";

    public BoneEmperor(Properties properties) {
        super(properties);
    }

    public static void hurt(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, Handler.mixinName("bone_emperor"))) {
                AttributeInstance instance = player.getAttribute(VowsAttributes.soulShieldMaxValue);
                if (instance != null) {
                    int soulShield = (int) instance.getValue();
                    if (soulShield > 0) {
                        player.getPersistentData().putInt(soulBoneEmperor, soulShield);
                        float doDamage = (float) Math.sqrt(soulShield);
                        doDamage /= 1.8f;
                        if (doDamage < 1) {
                            doDamage = 1;
                        }
                        if (doDamage > 10) {
                            doDamage = 10;
                        }
                        event.setNewDamage(event.getNewDamage() * (1 / doDamage));
                        if (event.getNewDamage() < 2) {
                            event.setNewDamage(2);
                        }
                    }
                }

            }
        }
    }

    @Override
    public void tickVows(LivingEntity entity) {
        super.tickVows(entity);
        entity.setData(VowsAttributes.soulShield,0);
    }

    @Override
    public String itemName() {
        return Handler.mixinName("bone_emperor");
    }
    @Override
    public List<RenderVowsItem.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,50,80,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_1.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,50,80,120),
                        Vows.fromNamespaceAndPath("textures/soul/soul_2.png")),
                new RenderVowsItem.ColorAndImage(Light.ARGB.color(255,100,175,255),
                        Vows.fromNamespaceAndPath("textures/soul/soul_22.png"))
        );
    }
    @Override
    public void applyText(ItemStack stack, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.applyText(stack, tooltipComponents, tooltipFlag);
        addText(tooltipComponents,Component.translatable("vows.vows.bone_emperor.1"),true);
        addText(tooltipComponents,Component.translatable("vows.vows.bone_emperor.2"),false);
    }

    @Override
    public Component textMain() {
        return Component.translatable("vows.vows.bone_emperor");
    }
    @RecipePlugin
    public static class Recipe implements RegisterRecipeConfig {

        @Override
        public List<ItemStack> itemList() {
            return List.of(
                    Items.ENCHANTED_GOLDEN_APPLE.getDefaultInstance(),
                    Items.MUSIC_DISC_13.getDefaultInstance(),
                    Items.FERMENTED_SPIDER_EYE.getDefaultInstance()
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
            return Handler.mixinName("bone_emperor");
        }
    }

}


