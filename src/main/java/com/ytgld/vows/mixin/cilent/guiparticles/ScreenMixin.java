package com.ytgld.vows.mixin.cilent.guiparticles;


import com.ytgld.vows.client.gui_particles.BlackParticlesRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(at = @At(value = "RETURN"),method = "render")
    private void extractRenderState(GuiGraphics p_281549_, int p_281550_, int p_282878_, float p_282465_, CallbackInfo ci) {
        BlackParticlesRenderer.onRenderGui(p_281549_,p_281549_.pose());
    }
}
