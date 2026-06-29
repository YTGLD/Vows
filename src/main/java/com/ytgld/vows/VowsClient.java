package com.ytgld.vows;

import com.ytgld.vows.block.VowsBlockEntitys;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.client.block.VowsBlockEntityRenderer;
import com.ytgld.vows.client.partclie.ColorPart;
import com.ytgld.vows.client.partclie.Particles;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Vows.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Vows.MODID, value = Dist.CLIENT)
public class VowsClient {
    public static int time;
    public VowsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                VowsBlockEntitys.VowsBlockEntity_.get(),
                VowsBlockEntityRenderer::new
        );
    }


    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(Particles.ColorOption_.get(), ColorPart.Provider::new);
    }
    @SubscribeEvent
    public static void EntityRenderersEvent(ClientTickEvent.Pre event) {
        time++;
        RenderVowsItem.clientTick(event);
    }
}
