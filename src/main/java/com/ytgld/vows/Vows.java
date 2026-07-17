package com.ytgld.vows;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.logging.LogUtils;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.capability.ModCapabilities;
import com.ytgld.vows.client.CIStateShardsHasBlack;
import com.ytgld.vows.client.VRender;
import com.ytgld.vows.event.VowsEvent;
import com.ytgld.vows.items.VowsItems;
import com.ytgld.vows.items.VowsTab;
import com.ytgld.vows.tool.Handler;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Set;

@Mod(Vows.MODID)
public class Vows {
    public static final String MODID = "vows";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Vows(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        MinecraftForge.EVENT_BUS.register(new VowsAttributes());
        MinecraftForge.EVENT_BUS.register(new VowsEvent());
        MinecraftForge.EVENT_BUS.register(new ModCapabilities());

        VowsItems.REGISTRY.register(modEventBus);
        VowsTab.REGISTRY.register(modEventBus);
        VowsAttributes.ATTRIBUTES.register(modEventBus);
    }
    public static ResourceLocation fromNamespaceAndPath(String  s){
        return new ResourceLocation(MODID,s);
    }
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void EntityRenderersEvent(RegisterShadersEvent event) {
            try {
                event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        Vows.fromNamespaceAndPath( "position_color_tex"),
                        DefaultVertexFormat.POSITION_COLOR_TEX), CIStateShardsHasBlack::setHasBlock);

                event.registerShader(new ShaderInstance(event.getResourceProvider(),
                        Vows.fromNamespaceAndPath( "live"),
                        DefaultVertexFormat.POSITION_COLOR_TEX), VRender::setShaderInstanceLive);

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
