package com.ytgld.vows.attributre;

import com.ytgld.vows.Vows;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Vows.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VowsAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES,Vows.MODID);

    public static final RegistryObject<Attribute> maxVows= ATTRIBUTES.register(
            "max_vows",()->new RangedAttribute(
                    "vows.attribute.max_vows", 3, 0, 100).setSyncable(true));
    public static final RegistryObject<Attribute> soulShieldMaxValue = ATTRIBUTES.register(
            "soul_shield",()->new RangedAttribute(
                    "vows.attribute.soul_shield", 0, 0, 100).setSyncable(true));


    public static final RegistryObject<Attribute> soulShieldHealSpeed = ATTRIBUTES.register(
            "soul_shield_speed",()->new RangedAttribute(
                    "vows.attribute.soul_shield_speed", 1, 0, 100).setSyncable(true));


    public static final RegistryObject<Attribute> soulShieldHealCooldown = ATTRIBUTES.register(
            "soul_shield_heal_cooldown",()->new RangedAttribute(
                    "vows.attribute.soul_shield_heal_cooldown", 10, 0, 1000).setSyncable(true));

    public static final RegistryObject<Attribute> soulShieldStronger= ATTRIBUTES.register(
            "soul_shield_stronger",()->new RangedAttribute(
                    "vows.attribute.soul_shield_stronger", 1, 0, 100).setSyncable(true));

    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeModificationEvent  event) {
        event.add(EntityType.PLAYER ,soulShieldMaxValue.get(),0);
        event.add(EntityType.PLAYER ,soulShieldHealSpeed.get(),1);
        event.add(EntityType.PLAYER ,soulShieldHealCooldown.get(),10);
        event.add(EntityType.PLAYER ,soulShieldStronger.get(),1);
        event.add(EntityType.PLAYER ,maxVows.get(),3);
    }

}
