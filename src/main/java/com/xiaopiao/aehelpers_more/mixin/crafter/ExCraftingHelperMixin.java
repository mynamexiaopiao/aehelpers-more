package com.xiaopiao.aehelpers_more.mixin.crafter;


import appeng.menu.me.items.CraftingTermMenu;
import appeng.util.CraftingRecipeUtil;
import com.glodblock.github.extendedae.container.ContainerExCraftingTerminal;
import com.glodblock.github.extendedae.xmod.jei.transfer.ExCraftingHelper;
import com.xiaopiao.aehelpers_more.client.AutoCraftingWatcher;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(ExCraftingHelper.class)
public class ExCraftingHelperMixin {
    @Inject(method = "performTransfer", at = @At("HEAD") , remap = false)
    private static void ae2extras$onPerformTransfer(ContainerExCraftingTerminal menu, Recipe<?> recipe, int recipeSize, boolean craftMissing, CallbackInfo ci) {
        if (craftMissing && AutoCraftingWatcher.INSTANCE.isAutoInsertEnabled()) {
            var ingredients = CraftingRecipeUtil.ensure3by3CraftingMatrix(recipe);

            var slotToIngredientMap = new HashMap<Integer, Ingredient>();
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = ingredients.get(i);
                if (!ingredient.isEmpty()) {
                    slotToIngredientMap.put(i, ingredient);
                }
            }

            var missingResult = menu.findMissingIngredients(slotToIngredientMap);

            if (!missingResult.craftableSlots().isEmpty()) {
                for (var missing : missingResult.craftableSlots()) {
                    var ingredient = slotToIngredientMap.get(missing);
                    AutoCraftingWatcher.INSTANCE.setPending(
                            slotToIngredientMap,
                            missingResult.craftableSlots()
                    );
                }
            }
        }
    }
}
