package com.xiaopiao.aehelpers_more.network.packet;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.storage.StorageHelper;
import appeng.menu.SlotSemantics;
import appeng.menu.me.common.MEStorageMenu;
import appeng.menu.me.items.CraftingTermMenu;
import com.glodblock.github.extendedae.container.ContainerExCraftingTerminal;
import com.xiaopiao.aehelpers_more.AEHelpersMore;
import com.xiaopiao.aehelpers_more.mixin.accessor.MEStorageMenuAccessor;
import com.xiaopiao.aehelpers_more.mixin.crafter.ExCraftingHelperMixin;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record FillCraftingSlotPacket(int slotIndex, AEKey what) {

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(slotIndex);
//        what.writeToPacket(buffer);
        AEKey.writeKey(buffer, what);
    }

    public static FillCraftingSlotPacket decode(FriendlyByteBuf buffer) {
        int slotIndex = buffer.readVarInt();
        AEKey what = AEKey.readKey(buffer);
        return new FillCraftingSlotPacket(slotIndex, what);
    }

    public static void handle(FillCraftingSlotPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.containerMenu instanceof CraftingTermMenu menu &&
                    packet.what instanceof AEItemKey itemKey) {

                   extracted(packet, menu, itemKey);
            }else if (ModList.get().isLoaded("expatternprovider")) {
                if (player != null && player.containerMenu instanceof ContainerExCraftingTerminal menu &&
                        packet.what instanceof AEItemKey itemKey) {

                    extracted(packet, menu, itemKey);
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }

    private static void extracted(FillCraftingSlotPacket packet, MEStorageMenu menu, AEItemKey itemKey) {
        if (packet.slotIndex < 0 || packet.slotIndex >= menu.slots.size()) return;

        var targetSlot = menu.getSlots(SlotSemantics.CRAFTING_GRID).get(packet.slotIndex);

        if (targetSlot.hasItem()) return;

        long extracted = StorageHelper.poweredExtraction(
                ((MEStorageMenuAccessor) menu).getPowerSource(),
                menu.getHost().getInventory(),
                itemKey,
                1,
                menu.getActionSource()
        );

        if (extracted > 0) {
            ItemStack stack = itemKey.toStack((int) extracted);
            targetSlot.set(stack);
            menu.broadcastChanges();
        } else {
            AEHelpersMore.LOGGER.warn("无法从主机提取物品以填充格子: " + packet.what);
        }
    }
}