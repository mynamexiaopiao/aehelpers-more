package com.xiaopiao.aemore;


import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // 配置项：是否启用自动导入功能
    public static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_IMPORT = BUILDER
            .comment("Whether the auto import of scheduled crafting results is enabled. Also configurable via crafting menu UI.")
            .define("enableAutoImport", true);

    // 构建配置规范
    public static final ForgeConfigSpec SPEC = BUILDER.build();


    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC);
    }

}
