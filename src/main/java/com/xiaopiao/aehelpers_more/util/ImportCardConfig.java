package com.xiaopiao.aehelpers_more.util;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public record ImportCardConfig(boolean resultsOnly, boolean syncToGrid, @Nullable Direction overriddenDirection) {

    public static final ImportCardConfig DEFAULT = new ImportCardConfig(true, true, null);


    // 从 NBT 读取配置
    public static ImportCardConfig fromNBT(CompoundTag tag) {
        boolean resultsOnly = tag.getBoolean("resultsOnly");
        boolean syncToGrid = tag.getBoolean("syncToGrid");
        String directionStr = tag.getString("direction");
        Direction direction = null;
        if (directionStr != null && !directionStr.isEmpty() && !directionStr.equals("null")) {
            try {
                direction = Direction.valueOf(directionStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                direction = null;
            }
        }

        return new ImportCardConfig(resultsOnly, syncToGrid, direction);
    }

    // 将配置写入 NB

    public CompoundTag toNBT(CompoundTag tag) {
        tag.putBoolean("resultsOnly", resultsOnly);
        tag.putBoolean("syncToGrid", syncToGrid);
        tag.putString("direction", overriddenDirection == null ? "null" : overriddenDirection.getName());
        return tag;
    }



}
