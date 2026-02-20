package com.xiaopiao.aehelpers_more.mixin;

import appeng.api.networking.energy.IEnergySource;
import appeng.menu.me.common.MEStorageMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MEStorageMenu.class)
public interface MEStorageMenuAccessor {

    @Accessor("powerSource")
    IEnergySource getPowerSource();
}
