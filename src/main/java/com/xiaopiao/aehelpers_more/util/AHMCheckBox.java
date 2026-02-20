package com.xiaopiao.aehelpers_more.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class AHMCheckBox extends AbstractButton {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");

    private static final int TEXT_COLOR = 14737632;
    private static final int SPACING = 4;
    private static final int BOX_PADDING = 8;
    private boolean selected;
    private final boolean showLabel;
    private final OnValueChange onValueChange;
    private final MultiLineTextWidget textWidget;

    AHMCheckBox(int x, int y, int maxWidth, Component message, Font font, boolean selected , boolean showLabel,  OnValueChange onValueChange) {
        super(x, y, 0, 0, message);
        this.width = this.getAdjustedWidth(maxWidth, message, font);
        this.textWidget = (new MultiLineTextWidget(message, font)).setMaxWidth(this.width).setColor(14737632);
        this.height = this.getAdjustedHeight(font);
        this.selected = selected;
        this.onValueChange = onValueChange;
        this.showLabel = showLabel;
    }

    private int getAdjustedWidth(int maxWidth, Component message, Font font) {
        return Math.min(getDefaultWidth(message, font), maxWidth);
    }

    private int getAdjustedHeight(Font font) {
        return Math.max(getBoxSize(font), this.textWidget.getHeight());
    }

    static int getDefaultWidth(Component message, Font font) {
        return getBoxSize(font) + 4 + font.width(message);
    }

    public static Builder builder(Component message, Font font) {
        return new Builder(message, font);
    }

    public static int getBoxSize(Font font) {
        return 17;
    }

    public void onPress() {
        this.selected = !this.selected;
        this.onValueChange.onValueChange(this, this.selected);
    }

    public boolean selected() {
        return this.selected;
    }

    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.focused"));
            } else {
                narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.hovered"));
            }
        }

    }

    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.enableDepthTest();
        Font font = minecraft.font;
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        guiGraphics.blit(TEXTURE, this.getX(), this.getY(), this.isFocused() ? 20.0F : 0.0F, this.selected ? 20.0F : 0.0F, 20, this.height, 64, 64);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.showLabel) {
            guiGraphics.drawString(font, this.getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        private final Component message;
        private final Font font;
        private int maxWidth;
        private int x = 0;
        private int y = 0;
        private OnValueChange onValueChange;
        private boolean selected;
        @Nullable
        private OptionInstance<Boolean> option;
        @Nullable
        private Tooltip tooltip;

        Builder(Component message, Font font) {
            this.onValueChange = AHMCheckBox.OnValueChange.NOP;
            this.selected = false;
            this.option = null;
            this.tooltip = null;
            this.message = message;
            this.font = font;
            this.maxWidth = AHMCheckBox.getDefaultWidth(message, font);
        }

        public Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder onValueChange(OnValueChange onValueChange) {
            this.onValueChange = onValueChange;
            return this;
        }

        public Builder selected(boolean selected) {
            this.selected = selected;
            this.option = null;
            return this;
        }

        public Builder selected(OptionInstance<Boolean> option) {
            this.option = option;
            this.selected = (Boolean)option.get();
            return this;
        }

        public Builder tooltip(Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder maxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return this;
        }

        public AHMCheckBox build() {
            OnValueChange checkbox$onvaluechange = this.option == null ? this.onValueChange : (p_309064_, p_308939_) -> {
                this.option.set(p_308939_);
                this.onValueChange.onValueChange(p_309064_, p_308939_);
            };
            AHMCheckBox checkbox = new AHMCheckBox(this.x, this.y, this.maxWidth, this.message, this.font, this.selected,true ,  checkbox$onvaluechange  );
            checkbox.setTooltip(this.tooltip);
            return checkbox;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnValueChange {
        OnValueChange NOP = (p_309046_, p_309014_) -> {
        };

        void onValueChange(AHMCheckBox var1, boolean var2);
    }
}
