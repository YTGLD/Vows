package com.ytgld.vows.mixin.cilent.blacksoul;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.vows.client.RenderVowsItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsExtractorMixin {
    @Shadow
    public abstract PoseStack pose();

    @Inject(
            at = @At("RETURN"),
            method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V"
    )
    public void renderItem(
            LivingEntity p_282619_, Level p_281754_, ItemStack p_281675_, int p_281271_, int p_282210_, int p_283260_, int p_281995_, CallbackInfo ci
    ) {
        GuiGraphics guiGraphicsExtractor = (GuiGraphics) (Object) this;
        RenderVowsItem.renderItem(guiGraphicsExtractor,pose(),p_281675_,p_281271_,p_282210_,p_281995_);
    }
}
