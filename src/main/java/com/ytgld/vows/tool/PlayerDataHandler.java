package com.ytgld.vows.tool;

import com.mojang.serialization.Codec;
import com.ytgld.vows.Vows;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
                    .serialize(StringSetCodec.CODEC.fieldOf("vows_set").codec())
                    .build()
    );

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
