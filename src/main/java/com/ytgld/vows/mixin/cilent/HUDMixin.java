package com.ytgld.vows.mixin.cilent;

import com.ytgld.vows.client.RenderSoulShield;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class HUDMixin {
    @Shadow
    public int leftHeight;

    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Inject(at = @At(value = "RETURN"),method = "extractArmorLevel")
    private void renderItem(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        this.leftHeight = RenderSoulShield.setLeftHeight(this.leftHeight,this.getCameraPlayer());
        RenderSoulShield.renderArmorLevel(graphics,this.getCameraPlayer(),this.leftHeight);

    }
}
