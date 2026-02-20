package com.xiaopiao.aemore.network;


import com.xiaopiao.aemore.AEHelpersMore;
import com.xiaopiao.aemore.common.register.ModItems;
import com.xiaopiao.aemore.util.ImportCardConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateImportCardPacket {
    private final ImportCardConfig config;

    public UpdateImportCardPacket(ImportCardConfig config) {
        this.config = config;
    }

    public ImportCardConfig getConfig() {
        return config;
    }

    public static void encode(UpdateImportCardPacket packet, FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        packet.config.toNBT(tag);
        buffer.writeNbt(tag);
    }

    public static UpdateImportCardPacket decode(FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();
        if (tag == null) {
            tag = new CompoundTag();
        }
        ImportCardConfig config = ImportCardConfig.fromNBT(tag);
        return new UpdateImportCardPacket(config);
    }

    public static void handle(UpdateImportCardPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ItemStack heldItem = player.getMainHandItem();

                if (heldItem.getItem() == ModItems.RESULT_IMPORT_CARD.get()) {
                    CompoundTag tag = heldItem.getOrCreateTag();
                    CompoundTag nbt = packet.config.toNBT(tag);
                    heldItem.setTag(nbt);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}