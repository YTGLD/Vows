package com.ytgld.vows;

import com.ytgld.vows.event.VowsEvent;
import com.ytgld.vows.items.VowsItems;
import com.ytgld.vows.tool.DataReg;
import com.ytgld.vows.tool.PlayerDataHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(Vows.MODID)
public class Vows {
    public static final String MODID = "vows";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Vows(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(new VowsEvent());


        PlayerDataHandler.ATTACHMENT_TYPES.register(modEventBus);
        DataReg.REGISTRY.register(modEventBus);
        VowsItems.REGISTRY.register(modEventBus);
    }
    public static ResourceLocation fromNamespaceAndPath(String  s){
        return ResourceLocation.fromNamespaceAndPath(MODID,s);
    }

}
