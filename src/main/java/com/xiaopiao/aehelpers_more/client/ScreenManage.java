package com.xiaopiao.aehelpers_more.client;

import appeng.client.gui.me.crafting.CraftingCPUScreen;
import appeng.client.gui.me.items.CraftingTermScreen;
import com.glodblock.github.extendedae.client.gui.GuiExCraftingTerminal;
import com.xiaopiao.aehelpers_more.integration.PacketUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ScreenManage {

    private static boolean isCreatingScreen = false;
    private static boolean isCPUScreen = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc != null){
            Screen screen = mc.screen;

            if (screen == null) {
                isCreatingScreen = false;
                isCPUScreen = false;
            }

            if (screen instanceof CraftingTermScreen<?>){
                isCreatingScreen = true;
            }

            if (PacketUtil.isInstance(PacketUtil.GUIEXCRAFTINGTERMINAL , screen)){
                isCreatingScreen = true;
            }


            if (isCreatingScreen){
                if (screen instanceof CraftingCPUScreen<?>){
                    isCPUScreen = true;
                    isCreatingScreen = false;
                }
            }

            if (!isCPUScreen && !isCreatingScreen){
                AutoCraftingWatcher.INSTANCE.clear();
            }

        }

    }
}
