package com.xiaopiao.aehelpers_more.integration;

public class PacketUtil {

    public static final String EXCRAFTINGHELPER = "com.glodblock.github.extendedae.container.ContainerExCraftingTerminal";
    public static final String GUIEXCRAFTINGTERMINAL = "com.glodblock.github.extendedae.client.gui.GuiExCraftingTerminal";


    public static boolean isInstance(String s , Object object) {
        try {
            Class<?> aClass = Class.forName(s);

            return aClass.isInstance(object);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }


}
