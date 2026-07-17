package com.ytgld.vows.items;

import com.ytgld.vows.Vows;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VowsTab {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Vows.MODID);
    public static final RegistryObject<CreativeModeTab> VOWS =
            REGISTRY.register("vows",()->{
                return CreativeModeTab.builder()
                        .title(Component.translatable("vows.vows"))
                        .icon(()->new ItemStack(VowsItems.Alms_.get()))
                        .displayItems((a,b)->{

                            b.accept(VowsItems.FamineConstraints_.get());
                            b.accept(VowsItems.ChecksBalances_.get());
                            b.accept(VowsItems.Alms_.get());
                            b.accept(VowsItems.BreakSword_.get());
                            b.accept(VowsItems.WarFortress_.get());
                            b.accept(VowsItems.DeathString_.get());
                            b.accept(VowsItems.SoulLife_.get());

                            b.accept(VowsItems.BreakHeart_.get());
                            b.accept(VowsItems.BoneEmperor_.get());
                        })
                        .build();
            });
}
