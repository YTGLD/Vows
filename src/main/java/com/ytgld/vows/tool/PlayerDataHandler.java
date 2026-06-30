package com.ytgld.vows.tool;

import com.mojang.serialization.Codec;
import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.IntSyncHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class PlayerDataHandler {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES
            = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Vows.MODID);

    public static final Supplier<AttachmentType<Set<String>>> vVowsSet = ATTACHMENT_TYPES.register(
            "vows_set",
            () -> AttachmentType.<Set<String>>builder(() -> new HashSet<>())
                    .sync(new StringSetSync())
                    .serialize(StringSetCodec.CODEC.fieldOf("vows_set"))
                    .build()
    );
    public static final Supplier<AttachmentType<String>> trueVowsBlock = ATTACHMENT_TYPES.register(
            "vows_set_block",
            () -> AttachmentType.builder(() -> "")
                    .sync(ByteBufCodecs.STRING_UTF8)
                    .serialize(Codec.STRING.fieldOf("vows_set_block"))
                    .build()
    );

    public static final Supplier<AttachmentType<Integer>> ineAlpha = ATTACHMENT_TYPES.register(
            "alpha",
            () -> AttachmentType.builder(() -> 0)
                    .sync(new IntSyncHandler())
                    .serialize(Codec.INT.fieldOf("alpha"))
                    .build()
    );
    public static final Supplier<AttachmentType<CompoundTag>> vowsCompoundTag = ATTACHMENT_TYPES.register(
            "entity_tag",
            () -> AttachmentType.builder(() -> new CompoundTag())
                    .sync(new CompoundTagSync())
                    .serialize(CompoundTag.CODEC.fieldOf("entity_tag"))
                    .build()
    );
    public static final Supplier<AttachmentType<IntAndStringSyncHandler.ISClass>> theIntAndStringSyncHandler = ATTACHMENT_TYPES.register(
            "counter", () -> AttachmentType.builder(()->new IntAndStringSyncHandler.ISClass(new HashMap<>()))
                    .sync(new IntAndStringSyncHandler()).serialize(IntAndStringSyncHandler.CODEC.
                            fieldOf("counter")).build()
    );
    public static class CompoundTagSync implements AttachmentSyncHandler<CompoundTag> {

        @Override
        public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
            return holder == to;
        }

        @Override
        public void write(RegistryFriendlyByteBuf registryFriendlyByteBuf, CompoundTag compoundTag, boolean b) {
            registryFriendlyByteBuf.writeNbt(compoundTag);
        }

        @Override
        public @Nullable CompoundTag read(IAttachmentHolder iAttachmentHolder, RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable CompoundTag compoundTag) {
            return registryFriendlyByteBuf.readNbt();
        }
    }


    public static class StringSetSync implements AttachmentSyncHandler<Set<String>> {

        @Override
        public void write(RegistryFriendlyByteBuf buf, Set<String> attachment, boolean initialSync) {
            buf.writeVarInt(attachment.size());
            for (String s : attachment) {
                buf.writeUtf(s);
            }
        }

        @Override
        @Nullable
        public Set<String> read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable Set<String> previousValue) {
            int size = buf.readVarInt();
            Set<String> set = previousValue != null ? previousValue : new HashSet<>();
            set.clear();
            for (int i = 0; i < size; i++) {
                set.add(buf.readUtf(32767));
            }
            return set;
        }

        @Override
        public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
            return holder == to;
        }
    }

    // Codec 用于序列化到 NBT
    public static class StringSetCodec {
        public static final Codec<Set<String>> CODEC = Codec.STRING.listOf()
                .xmap(HashSet::new, ArrayList::new);
    }
}
