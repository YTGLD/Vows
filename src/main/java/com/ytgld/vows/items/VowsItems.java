package com.ytgld.vows.items;

import com.ytgld.vows.Vows;
import com.ytgld.vows.vowsitems.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VowsItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Vows.MODID);

    public static final RegistryObject<Item > FamineConstraints_ =
            REGISTRY.register("famine_constraints",()->new FamineConstraints(new Item.Properties()));

    public static final RegistryObject<Item > ChecksBalances_ =
            REGISTRY.register("checks_balances",()->new ChecksBalances(new Item.Properties()));

    public static final RegistryObject<Item > Alms_ =
            REGISTRY.register("alms",()->new Alms(new Item.Properties()));

    public static final RegistryObject<Item > BreakSword_ =
            REGISTRY.register("break_sword",()->new BreakSword(new Item.Properties()));

    public static final RegistryObject<Item > WarFortress_ =
            REGISTRY.register("war_fortress",()->new WarFortress(new Item.Properties()));

    public static final RegistryObject<Item > DeathString_ =
            REGISTRY.register("death_string",()->new DeathString(new Item.Properties()));

    public static final RegistryObject<Item > SoulLife_ =
            REGISTRY.register("soul_life",()->new SoulLife(new Item.Properties()));

    public static final RegistryObject<Item > BreakHeart_ =
            REGISTRY.register("break_heart",()->new BreakHeart(new Item.Properties()));

    public static final RegistryObject<Item > BoneEmperor_ =
            REGISTRY.register("bone_emperor",()->new BoneEmperor(new Item.Properties()));

}
