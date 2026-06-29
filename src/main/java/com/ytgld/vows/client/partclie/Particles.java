package com.ytgld.vows.client.partclie;

import com.mojang.serialization.MapCodec;
import com.ytgld.vows.Vows;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Particles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Vows.MODID);
    public static final DeferredHolder<ParticleType<?>, ParticleType<ColorOption>> ColorOption_ =
            PARTICLE_TYPES.register("color",
                    () -> new ParticleType<>(false) {
                        @Override
                        public MapCodec<ColorOption> codec() {
                            return ColorOption.CODEC;
                        }

                        @Override
                        public StreamCodec<? super RegistryFriendlyByteBuf, ColorOption> streamCodec() {
                            return ColorOption.STREAM_CODEC;
                        }
                    });
}
