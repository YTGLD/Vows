package com.ytgld.vows.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.ytgld.vows.Vows;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.event.RegisterShadersEvent;

import java.io.IOException;

public class VRender  {
    private static ShaderInstance shaderInstanceLive;

    public static void setShaderInstanceLive(ShaderInstance shaderInstanceLive) {
        VRender.shaderInstanceLive = shaderInstanceLive;
    }

    public static ShaderInstance getShaderInstanceLive() {
        return shaderInstanceLive;
    }
}
