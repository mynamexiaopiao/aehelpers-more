package com.xiaopiao.aehelpers_more.mixin.crafter;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.me.common.MEStorageScreen;
import appeng.client.gui.style.ScreenStyle;
import appeng.menu.me.common.MEStorageMenu;
import appeng.menu.me.items.CraftingTermMenu;
import com.glodblock.github.extendedae.client.gui.GuiExCraftingTerminal;
import com.glodblock.github.extendedae.container.ContainerExCraftingTerminal;
import com.xiaopiao.aehelpers_more.client.AutoCraftingWatcher;
import com.xiaopiao.aehelpers_more.client.AutoInsertButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MEStorageScreen.class, priority = 2500)
public abstract class MEStorageScreenMixin<C extends MEStorageMenu> extends AEBaseScreen<C> {

    public MEStorageScreenMixin(C menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
    }

    @Inject(method = "containerTick", at = @At("RETURN"))
    private void ae2extras$onContainerTick(CallbackInfo ci) {
        MEStorageScreen<?> screen = (MEStorageScreen<?>) (Object) this;
        AutoCraftingWatcher.INSTANCE.onTick(screen);
    }
    
    @Inject(method = "removed", at = @At("HEAD"))
    private void ae2extras$onRemoved(CallbackInfo ci) {
        AutoCraftingWatcher.INSTANCE.onScreenRemoved();
    }
    
    @Inject(method = "renderSlot", at = @At("HEAD"))
    private void ae2extras$onRenderSlot(GuiGraphics guiGraphics, Slot slot, CallbackInfo ci) {
        AutoCraftingWatcher.INSTANCE.renderGhosts(guiGraphics, slot);
    }
    
    @Inject(method = "<init>(Lappeng/menu/me/common/MEStorageMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;Lappeng/client/gui/style/ScreenStyle;)V", at = @At("TAIL"))
    private void ae2extras$onInit(CallbackInfo ci) {
        var screen = (MEStorageScreen<?>) (Object) this;

        if (screen.getMenu() instanceof CraftingTermMenu) {
            this.addToLeftToolbar(new AutoInsertButton(this::ae2helpers$onToggleAutoInsert));
        }
        if (ModList.get().isLoaded("expatternprovider")) {
            if (screen.getMenu() instanceof ContainerExCraftingTerminal){
                this.addToLeftToolbar(new AutoInsertButton(this::ae2helpers$onToggleAutoInsert));
            }
        }
    }
    
    @Unique
    private void ae2helpers$onToggleAutoInsert(Button button) {
        AutoCraftingWatcher.INSTANCE.toggleAutoInsert();
    }
}