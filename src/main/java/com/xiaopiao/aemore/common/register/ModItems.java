package com.xiaopiao.aemore.common.register;

import com.xiaopiao.aemore.AEHelpersMore;
import com.xiaopiao.aemore.common.items.ImportCardItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AEHelpersMore.MODID);

    public static final RegistryObject<Item> RESULT_IMPORT_CARD = ITEMS.register("result_import_card", () -> new ImportCardItem(new Item.Properties()));

}
