package com.xiaopiao.aehelpers_more.util;

import appeng.api.upgrades.IUpgradeInventory;

// this basically replaces IUpgradeableObject to ensure compatibility with other mods that also add an upgrade slot
public interface IPatternProviderUpgradeHost {
    IUpgradeInventory ae2helpers$getUpgradeInventory();
}