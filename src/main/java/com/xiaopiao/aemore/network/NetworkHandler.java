package com.xiaopiao.aemore.network;

import com.xiaopiao.aemore.AEHelpersMore;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1.0";
    private static SimpleChannel CHANNEL;
    private static boolean registered = false;
    private static int packetId = 0;

    private static int getNextId() {
        return packetId++;
    }

    public static SimpleChannel getChannel() {
        if (CHANNEL == null) {
            CHANNEL = NetworkRegistry.ChannelBuilder.named(
                            new ResourceLocation(AEHelpersMore.MODID, "main"))
                    .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                    .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                    .networkProtocolVersion(() -> PROTOCOL_VERSION)
                    .simpleChannel();
        }
        return CHANNEL;
    }

    public static void register() {
        if (registered) return;
        
        packetId = 0;

        getChannel().messageBuilder(FillCraftingSlotPacket.class, getNextId())
                .encoder(FillCraftingSlotPacket::encode)
                .decoder(FillCraftingSlotPacket::decode)
                .consumerMainThread(FillCraftingSlotPacket::handle)
                .add();

        getChannel().messageBuilder(UpdateImportCardPacket.class, getNextId())
                .encoder(UpdateImportCardPacket::encode)
                .decoder(UpdateImportCardPacket::decode)
                .consumerMainThread(UpdateImportCardPacket::handle)
                .add();

        registered = true;
        AEHelpersMore.LOGGER.info("Registered {} network packets", packetId);
    }
    public static void sendToServer(Object msg) {
        getChannel().send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToClient(Object msg, PacketDistributor.PacketTarget target) {
        getChannel().send(target, msg);
    }

    public static void sendToPlayer(Object msg, net.minecraft.server.level.ServerPlayer player) {
        getChannel().send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
