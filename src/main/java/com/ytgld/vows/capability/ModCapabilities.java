package com.ytgld.vows.capability;

import com.ytgld.vows.Vows;
import com.ytgld.vows.capability.soulshield.cap.ISwordCapability;
import com.ytgld.vows.capability.soulshield.cap.SoulShieldCapabilityProvider;
import com.ytgld.vows.capability.vowsset.IVowsCapability;
import com.ytgld.vows.capability.vowsset.VowsCapabilityProvider;
import com.ytgld.vows.tool.Handler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

public class ModCapabilities {

    public static final Capability<ISwordCapability> TAG_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IVowsCapability> theIVowsCapability = CapabilityManager.get(new CapabilityToken<>() {});
    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {

        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal()
                    .getCapability(ModCapabilities.theIVowsCapability)
                    .ifPresent(oldCap -> {
                        event.getEntity()
                                .getCapability(ModCapabilities.theIVowsCapability)
                                .ifPresent(newCap -> {
                                    newCap.getValueSwordStronger().clear();

                                    newCap.getValueSwordStronger()
                                            .addAll(oldCap.getValueSwordStronger());
                                });
                    });
        }
    }
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    new ResourceLocation(Vows.MODID, "soul_shield"),
                    new SoulShieldCapabilityProvider()
            );
        }
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    new ResourceLocation(Vows.MODID, "vows"),
                    new VowsCapabilityProvider()
            );
        }
    }
    public static Set<String> getVows(LivingEntity livingEntity){
        if (livingEntity.getCapability(ModCapabilities.theIVowsCapability).resolve().isPresent()) {
            return livingEntity.getCapability(ModCapabilities.theIVowsCapability).resolve().get().getValueSwordStronger();
        }
        return Set.of();
    }

    public static void setSoulShieldCapability(LivingEntity livingEntity, int string){
        livingEntity.getCapability(ModCapabilities.TAG_CAPABILITY).ifPresent(cap -> {
            cap.setValueSwordStronger(string);
        });
    }
    public static int getoulShieldCapability(LivingEntity livingEntity){
        if (livingEntity.getCapability(ModCapabilities.TAG_CAPABILITY).resolve().isPresent()) {
            return livingEntity.getCapability(ModCapabilities.TAG_CAPABILITY).resolve().get().getValueSwordStronger();
        }
        return 0;
    }
}