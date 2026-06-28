package com.ytgld.vows;

import com.ytgld.vows.client.RenderVowsItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Vows.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Vows.MODID, value = Dist.CLIENT)
public class VowsClient {
    public VowsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    public static void EntityRenderersEvent(ClientTickEvent.Pre event) {
        RenderVowsItem.clientTick(event);
    }
}
