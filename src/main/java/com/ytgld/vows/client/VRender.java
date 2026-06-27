package com.ytgld.vows.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.ytgld.vows.Vows;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

public class VRender {
    private static ShaderInstance shaderInstanceLive;
    private static ShaderInstance shaderInstanceHasBlack;

    public static void setShaderInstanceLive(ShaderInstance shaderInstanceLive) {
        VRender.shaderInstanceLive = shaderInstanceLive;
    }

    public static void setShaderInstanceHasBlack(ShaderInstance shaderInstanceHasBlack) {
        VRender.shaderInstanceHasBlack = shaderInstanceHasBlack;
    }

    public static ShaderInstance getShaderInstanceLive() {
        return shaderInstanceLive;
    }

    public static ShaderInstance getShaderInstanceHasBlack() {
        return shaderInstanceHasBlack;
    }

    public static void RegisterShadersEvent(RegisterShadersEvent event){
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(),
                    Vows.fromNamespaceAndPath("live"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), VRender::setShaderInstanceLive);
            event.registerShader(new ShaderInstance(event.getResourceProvider(),
                    Vows.fromNamespaceAndPath("position_tex_color_black"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), VRender::setShaderInstanceHasBlack);

        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
