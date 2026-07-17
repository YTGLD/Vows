package com.ytgld.vows.mixin.cilent;

import com.ytgld.vows.client.gui_particles.BlackKey;
import com.ytgld.vows.client.gui_particles.BlackParticlesAdd;
import com.ytgld.vows.items.BaseVows;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
    @Shadow
    protected int topPos;
    @Shadow
    protected int leftPos;

    @Inject(at = @At(value = "HEAD"), method = "renderSlot")
    private void renderSlotContents(
            GuiGraphics p_281607_, Slot p_282613_, CallbackInfo ci
    ) {
        if (p_282613_.getItem().isEmpty()) return;
        if (!(p_282613_.getItem().getItem() instanceof BaseVows)) return;
        int screenX = p_282613_.x + leftPos;
        int screenY = p_282613_.y + topPos;

    }
}
