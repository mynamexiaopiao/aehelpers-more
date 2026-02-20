package com.xiaopiao.aehelpers_more.client;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

// because the source method is also loaded on the server (but never called), a separate helper class is needed
public class ImportCardClientHelper {

    public static void openScreen(ItemStack stack) {
//        // 从 NBT 标签中读取配置数据
        CompoundTag tag = stack.getOrCreateTag();
//
//        if (!tag.contains(AEHelpersMore.IMPORT_CARD_CONFIG)) {
//            tag.put(AEHelpersMore.IMPORT_CARD_CONFIG, defaultNBT);
//        }

        if (!tag.contains("resultsOnly")){
            tag.putBoolean("resultsOnly", true);
        }else if (!tag.contains("syncToGrid")){
            tag.putBoolean("syncToGrid", true);
        }else if (!tag.contains("direction")){
            tag.putString("direction", "null");
        }

        // 打开屏幕
        Minecraft.getInstance().setScreen(new ImportCardScreen(stack));
    }
}