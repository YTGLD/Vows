package com.ytgld.vows.mixin.cilent.blacksoul;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.vows.client.RenderVowsItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsExtractorMixin {
    @Shadow
    @Final
    private PoseStack pose;
    @Inject(at = @At(value = "RETURN"),method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V")
    public void renderItem(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset, CallbackInfo ci) {
        GuiGraphics guiGraphicsExtractor = (GuiGraphics) (Object) this;
        RenderVowsItem.renderItem(guiGraphicsExtractor,pose,stack,x,y,seed);
    }
}
