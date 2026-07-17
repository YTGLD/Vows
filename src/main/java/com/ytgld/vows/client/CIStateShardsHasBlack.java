package com.ytgld.vows.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class CIStateShardsHasBlack {
    public static ShaderInstance hasBlock;
    public static ShaderInstance getHasBlock() {
        return hasBlock;
    }
    public static void setHasBlock(ShaderInstance hasBlock) {
        CIStateShardsHasBlack.hasBlock = hasBlock;
    }
}
