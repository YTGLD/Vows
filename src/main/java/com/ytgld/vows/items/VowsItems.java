package com.ytgld.vows.items;

import com.ytgld.vows.Vows;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VowsItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, Vows.MODID);

    public static final DeferredHolder<Item ,Item > Sacrificial_ =
            REGISTRY.register("sacrificial",(resourceLocation)->new Sacrificial(new Item.Properties()));
}
