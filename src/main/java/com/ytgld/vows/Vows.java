package com.ytgld.vows;

import com.mojang.logging.LogUtils;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.block.VowsBlockEntitys;
import com.ytgld.vows.block.VowsBlocks;
import com.ytgld.vows.client.partclie.Particles;
import com.ytgld.vows.event.VowsEvent;
import com.ytgld.vows.items.VowsItems;
import com.ytgld.vows.items.VowsTab;
import com.ytgld.vows.tool.DataReg;
import com.ytgld.vows.tool.PlayerDataHandler;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

@Mod(Vows.MODID)
public class Vows {
    public static final String MODID = "vows";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Vows(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(new VowsEvent());


        PlayerDataHandler.ATTACHMENT_TYPES.register(modEventBus);
        DataReg.REGISTRY.register(modEventBus);
        VowsItems.REGISTRY.register(modEventBus);
        VowsTab.REGISTRY.register(modEventBus);
        VowsAttributes.ATTRIBUTES.register(modEventBus);
        VowsAttributes.ATTACHMENT_TYPES.register(modEventBus);
        VowsBlockEntitys.REGISTER.register(modEventBus);
        VowsBlocks.REGISTER.register(modEventBus);
        Particles.PARTICLE_TYPES.register(modEventBus);
        NeoForge.EVENT_BUS.addListener(PlayerEvent.Clone.class,(event)->{
            if (event.isWasDeath() && event.getOriginal().hasData(PlayerDataHandler.vVowsSet)) {
                event.getEntity().getData(PlayerDataHandler.vVowsSet).clear();
                event.getEntity().getData(PlayerDataHandler.vVowsSet)
                        .addAll(event.getOriginal().getData(PlayerDataHandler.vVowsSet))
                ;
            }
        });
    }
    public static Identifier fromNamespaceAndPath(String  s){
        return Identifier.fromNamespaceAndPath(MODID,s);
    }

}
