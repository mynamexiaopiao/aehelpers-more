package com.xiaopiao.aemore.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record ImportCardConfig(boolean resultsOnly, boolean syncToGrid, @Nullable Direction overriddenDirection) {

    public static final ImportCardConfig DEFAULT = new ImportCardConfig(true, true, null);


    // 从 NBT 读取配置
    public static ImportCardConfig fromNBT(CompoundTag tag) {
        boolean resultsOnly = tag.getBoolean("resultsOnly");
        boolean syncToGrid = tag.getBoolean("syncToGrid");
        String direction1 = tag.getString("direction");
        Direction direction;
        if (direction1 == null){
            direction = null;
        }else {
            direction = direction1.equals("null") || direction1.isEmpty()  ? null : Direction.valueOf(direction1.toUpperCase());
        }
        return new ImportCardConfig(resultsOnly, syncToGrid, direction);
    }

    // 将配置写入 NBT
    public CompoundTag toNBT(CompoundTag tag) {
        tag.putBoolean("resultsOnly", resultsOnly);
        tag.putBoolean("syncToGrid", syncToGrid);
        tag.putString("direction", overriddenDirection == null ? "null" : overriddenDirection.getName());
        return tag;
    }



}
