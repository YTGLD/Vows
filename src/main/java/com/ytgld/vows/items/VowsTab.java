package com.ytgld.vows.items;

import com.ytgld.vows.Vows;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VowsTab {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Vows.MODID);
    public static final DeferredHolder<CreativeModeTab ,CreativeModeTab > VOWS =
            REGISTRY.register("vows",(Identifier)->{
                return CreativeModeTab.builder()
                        .title(Component.translatable("vows.vows"))
                        .icon(()->new ItemStack(VowsItems.Sacrificial_))
                        .displayItems((a,b)->{
                            b.accept(VowsItems.VowsBlockItem.get());

                            b.accept(VowsItems.Sacrificial_.get());
                            b.accept(VowsItems.HungryWolf_.get());
                            b.accept(VowsItems.FamineConstraints_.get());
                            b.accept(VowsItems.ChecksBalances_.get());
                            b.accept(VowsItems.Disaster_.get());
                            b.accept(VowsItems.Alms_.get());
                            b.accept(VowsItems.HeavenlyWrath_.get());
                            b.accept(VowsItems.BreakingFate_.get());
                            b.accept(VowsItems.BreakSword_.get());
                            b.accept(VowsItems.WarFortress_.get());
                            b.accept(VowsItems.DeathString_.get());
                        })
                        .build();
            });
}
