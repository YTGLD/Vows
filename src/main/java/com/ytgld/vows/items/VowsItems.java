package com.ytgld.vows.items;

import com.ytgld.vows.Vows;
import com.ytgld.vows.block.VowsBlocks;
import com.ytgld.vows.items.vows.hunger.*;
import com.ytgld.vows.items.vows.magic.CtrlMagic;
import com.ytgld.vows.items.vows.magic.SoulDrive;
import com.ytgld.vows.items.vows.shield.*;
import com.ytgld.vows.items.vows.war.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VowsItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, Vows.MODID);

    public static final DeferredHolder<Item ,Item > VowsBlockItem =
            REGISTRY.register("vows_block",(Identifier)->new BlockItem(VowsBlocks.VowsBlock_.get(),new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier))));

    public static final DeferredHolder<Item ,Item > Sacrificial_ =
            REGISTRY.register("sacrificial",(Identifier)->new Sacrificial(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > HungryWolf_ =
            REGISTRY.register("hungry_wolf",(Identifier)->new HungryWolf(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > FamineConstraints_ =
            REGISTRY.register("famine_constraints",(Identifier)->new FamineConstraints(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > ChecksBalances_ =
            REGISTRY.register("checks_balances",(Identifier)->new ChecksBalances(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > Disaster_ =
            REGISTRY.register("disaster",(Identifier)->new Disaster(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));


    public static final DeferredHolder<Item ,Item > Alms_ =
            REGISTRY.register("alms",(Identifier)->new Alms(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > HeavenlyWrath_ =
            REGISTRY.register("heavenly_wrath",(Identifier)->new HeavenlyWrath(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > BreakingFate_ =
            REGISTRY.register("breaking_fate",(Identifier)->new BreakingFate(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > BreakSword_ =
            REGISTRY.register("break_sword",(Identifier)->new BreakSword(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > WarFortress_ =
            REGISTRY.register("war_fortress",(Identifier)->new WarFortress(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > DeathString_ =
            REGISTRY.register("death_string",(Identifier)->new DeathString(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > Rage_ =
            REGISTRY.register("rage",(Identifier)->new Rage(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > SoulLife_ =
            REGISTRY.register("soul_life",(Identifier)->new SoulLife(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > Judge_ =
            REGISTRY.register("judge",(Identifier)->new Judge(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > BreakShield_ =
            REGISTRY.register("break_shield",(Identifier)->new BreakShield(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > BreakHeart_ =
            REGISTRY.register("break_heart",(Identifier)->new BreakHeart(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > BoneEmperor_ =
            REGISTRY.register("bone_emperor",(Identifier)->new BoneEmperor(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static final DeferredHolder<Item ,Item > CtrlMagic_ =
            REGISTRY.register("ctrl_magic",(Identifier)->new CtrlMagic(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));


    public static final DeferredHolder<Item ,Item > SoulDrive_ =
            REGISTRY.register("soul_drive",(Identifier)->new SoulDrive(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier))));



}
