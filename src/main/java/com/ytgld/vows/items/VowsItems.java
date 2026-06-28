package com.ytgld.vows.items;

import com.ytgld.vows.Vows;
import com.ytgld.vows.items.vows.FamineConstraints;
import com.ytgld.vows.items.vows.HungryWolf;
import com.ytgld.vows.items.vows.Sacrificial;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VowsItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, Vows.MODID);

    public static final DeferredHolder<Item ,Item > Sacrificial_ =
            REGISTRY.register("sacrificial",(Identifier)->new Sacrificial(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > HungryWolf_ =
            REGISTRY.register("hungry_wolf",(Identifier)->new HungryWolf(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > FamineConstraints_ =
            REGISTRY.register("famine_constraints",(Identifier)->new FamineConstraints(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));






}
