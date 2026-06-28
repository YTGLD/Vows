package com.ytgld.vows.attributre;

import com.mojang.serialization.Codec;
import com.ytgld.vows.Vows;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;
@EventBusSubscriber(modid = Vows.MODID)
public class VowsAttributes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Vows.MODID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Vows.MODID);


    public static final Supplier<AttachmentType<Integer>> soulShield = ATTACHMENT_TYPES.register(
            "soul_shield",
            () -> AttachmentType.builder(() -> 0)
                    .sync(new IntSyncHandler())
                    .serialize(Codec.INT.fieldOf("soul_shield"))
                    .build()
    );
    public static final Supplier<AttachmentType<Integer>> soulShieldCooldown = ATTACHMENT_TYPES.register(
            "soul_shield_cooldown",
            () -> AttachmentType.builder(() -> 0)
                    .sync(new IntSyncHandler())
                    .serialize(Codec.INT.fieldOf("soul_shield_cooldown"))
                    .build()
    );
    public static final DeferredHolder<Attribute,Attribute> soulShieldMaxValue = ATTRIBUTES.register(
            "soul_shield",()->new RangedAttribute(
                    "vows.attribute.soul_shield", 0, 0, 100).setSyncable(true));


    public static final DeferredHolder<Attribute,Attribute> soulShieldHealSpeed = ATTRIBUTES.register(
            "soul_shield_speed",()->new RangedAttribute(
                    "vows.attribute.soul_shield_speed", 1, 0, 100).setSyncable(true));


    public static final DeferredHolder<Attribute,Attribute> soulShieldHealCooldown = ATTRIBUTES.register(
            "soul_shield_heal_cooldown",()->new RangedAttribute(
                    "vows.attribute.soul_shield_heal_cooldown", 10, 0, 1000).setSyncable(true));

    public static final DeferredHolder<Attribute,Attribute> soulShieldStronger= ATTRIBUTES.register(
            "soul_shield_stronger",()->new RangedAttribute(
                    "vows.attribute.soul_shield_stronger", 1, 0, 100).setSyncable(true));


    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeModificationEvent event) {
        event.add(EntityTypes.PLAYER ,soulShieldMaxValue);
        event.add(EntityTypes.PLAYER ,soulShieldHealSpeed);
        event.add(EntityTypes.PLAYER ,soulShieldHealCooldown);
        event.add(EntityTypes.PLAYER ,soulShieldStronger);
    }
}
