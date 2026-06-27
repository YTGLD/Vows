package com.ytgld.vows;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.client.VRender;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.io.IOException;

@Mod(value = Vows.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Vows.MODID, value = Dist.CLIENT)
public class VowsClient {
    public VowsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    public static void EntityRenderersEvent(RegisterShadersEvent event) {
        VRender.RegisterShadersEvent(event);
    }
    @SubscribeEvent
    public static void EntityRenderersEvent(ClientTickEvent.Pre event) {
        RenderVowsItem.clientTick(event);
    }
}
