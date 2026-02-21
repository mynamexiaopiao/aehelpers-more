package com.xiaopiao.aehelpers_more;

import appeng.api.ids.AECreativeTabIds;
import appeng.api.upgrades.Upgrades;
import appeng.menu.SlotSemantic;
import appeng.menu.SlotSemantics;
import com.mojang.logging.LogUtils;
import com.xiaopiao.aehelpers_more.common.register.ModItems;
import com.xiaopiao.aehelpers_more.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AEHelpersMore.MODID)
public class AEHelpersMore {

    public static final String MODID = "aehelpers_more";

    public static final ResourceLocation CACHE_INV = new ResourceLocation("ae2:cache_inv");

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final SlotSemantic IMPORT_UPGRADE = SlotSemantics.register("IMPORT_UPGRADE", false);

    public AEHelpersMore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModItems.ITEMS.register(modEventBus);

        modEventBus.addListener(this::clientSetup);
        Config.register();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::injectToAETab);
//        modEventBus.addListener(this::registerPayloads);

//        ModLoadingContext.get().registerExtensionPoint(
//                ConfigScreenHandler.ConfigScreenFactory.class,
//                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> new ConfigScreen(parent))
//        );

    }

    private void commonSetup(FMLCommonSetupEvent event) {

        // ideally we'd define the machine(s) as target here, but that then breaks with other mods that add upgrades to the machine
        Upgrades.add(ModItems.RESULT_IMPORT_CARD.get(), ModItems.RESULT_IMPORT_CARD.get(), 1, "gui.ae2helpers.import_card");

        event.enqueueWork(() -> {
            NetworkHandler.register();
            LOGGER.info("Network packets registered");
        });
    }

    private void injectToAETab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AECreativeTabIds.MAIN) {
            event.accept(ModItems.RESULT_IMPORT_CARD);
        }
    }


    public static ResourceLocation makeId(String path) {
        return new ResourceLocation(MODID, path);
    }

    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event) {

    }
}
