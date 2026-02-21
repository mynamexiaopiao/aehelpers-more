package com.xiaopiao.aehelpers_more.util;


import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;

/**
 *
 */
public class ModCheckUtils {

    private static final LoadingModList MOD_LIST = LoadingModList.get();

    public static final String
            MODID_EAE = "expatternprovider";

    /**
     * 检查指定模组是否存在
     */
    public static boolean isLoaded(String modid) {
        return MOD_LIST != null && MOD_LIST.getModFileById(modid) != null;
    }



}