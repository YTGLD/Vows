package com.ytgld.vows.tool;

import com.ytgld.vows.client.VRender;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class VowsTooltipRenderUtil {

    public VowsTooltipRenderUtil() {
    }

    public static void extractTooltipBackground(GuiGraphicsExtractor graphics, int x, int y, int w, int h, Identifier back,Identifier frame) {
        int x0 = x - 3 - 9;
        int y0 = y - 3 - 9;
        int paddedWidth = w + 3 + 3 + 18;
        int paddedHeight = h + 3 + 3 + 18;
        graphics.blitSprite(VRender.renderPipeline, back, x0, y0, paddedWidth, paddedHeight);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, frame, x0, y0, paddedWidth, paddedHeight);
    }

}
