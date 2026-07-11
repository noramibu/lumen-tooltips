package me.noramibu.lumentooltips.tooltip.preview;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public record LumenContainerTooltipComponent(
    ItemStack[] items, int columns, int rows, int accentColor, int hiddenItems)
    implements TooltipComponent, ClientTooltipComponent {
  private static final int SLOT_SIZE = 18;
  private static final int HORIZONTAL_PADDING = 7;
  private static final int TOP_PADDING = 6;
  private static final int BOTTOM_PADDING = 7;
  private static final int PANEL_BACKGROUND = 0xF0100010;
  private static final int CONTAINER_BACKGROUND = 0xFFC6C6C6;
  private static final int CONTAINER_BORDER = 0xFF373737;
  private static final int CONTAINER_SLOT = 0xFF8B8B8B;
  private static final int TEXT_COLOR = 0xFFFFFFFF;

  public LumenContainerTooltipComponent {
    hiddenItems = Math.max(0, hiddenItems);
  }

  @Override
  public int getHeight(Font font) {
    return TOP_PADDING + this.rows * SLOT_SIZE + BOTTOM_PADDING;
  }

  @Override
  public int getWidth(Font font) {
    return HORIZONTAL_PADDING * 2 + this.columns * SLOT_SIZE;
  }

  @Override
  public void extractImage(
      Font font, int x, int y, int width, int height, GuiGraphicsExtractor graphics) {
    int panelWidth = getWidth(font);
    int panelHeight = getHeight(font);
    graphics.fill(
        x, y, x + panelWidth, y + panelHeight, ARGB.multiply(CONTAINER_BORDER, this.accentColor));
    graphics.fill(
        x + 2,
        y + 2,
        x + panelWidth - 2,
        y + panelHeight - 2,
        ARGB.multiply(CONTAINER_BACKGROUND, this.accentColor));
    for (int index = 0; index < this.items.length; index++) {
      int column = index % this.columns;
      int row = index / this.columns;
      int slotX = x + HORIZONTAL_PADDING + column * SLOT_SIZE;
      int slotY = y + TOP_PADDING + row * SLOT_SIZE;
      graphics.fill(
          slotX,
          slotY,
          slotX + SLOT_SIZE,
          slotY + SLOT_SIZE,
          ARGB.multiply(CONTAINER_BORDER, this.accentColor));
      graphics.fill(
          slotX + 1,
          slotY + 1,
          slotX + SLOT_SIZE - 1,
          slotY + SLOT_SIZE - 1,
          ARGB.multiply(CONTAINER_SLOT, this.accentColor));
      ItemStack item = this.items[index];
      if (!item.isEmpty()) {
        graphics.item(item, slotX + 1, slotY + 1, 0);
        graphics.itemDecorations(font, item, slotX + 1, slotY + 1);
      }
    }
    if (this.hiddenItems > 0) {
      graphics.centeredText(
          font,
          Component.literal("+" + this.hiddenItems),
          x + panelWidth - 13,
          y + panelHeight - 14,
          TEXT_COLOR);
    }
  }

  static void drawPanel(
      GuiGraphicsExtractor graphics, int x, int y, int width, int height, int accent) {
    graphics.fill(x, y, x + width, y + height, PANEL_BACKGROUND);
    graphics.fill(x, y, x + width, y + 1, accent);
    graphics.fill(x, y + height - 1, x + width, y + height, accent);
    graphics.fill(x, y, x + 1, y + height, accent);
    graphics.fill(x + width - 1, y, x + width, y + height, accent);
  }
}
