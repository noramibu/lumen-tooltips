package me.noramibu.lumentooltips.client.screen;

import java.util.List;
import me.noramibu.lumentooltips.client.LumenItemEditor;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public final class LumenContainerScreen extends ContainerScreen {
  private static final int COLUMNS = 9;
  private static final int MAX_ROWS = 6;

  LumenContainerScreen(
      ItemStack containerItem, List<ItemStack> containerItems, Inventory playerInventory) {
    super(
        createMenu(containerItems, playerInventory),
        playerInventory,
        containerItem.getHoverName());
  }

  @Override
  public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
    return false;
  }

  @Override
  public boolean mouseReleased(MouseButtonEvent event) {
    return false;
  }

  @Override
  public boolean keyPressed(KeyEvent event) {
    if (this.hoveredSlot != null) {
      ItemStack stack = this.hoveredSlot.getItem();
      if (LumenItemEditor.trySaveToStorage(stack, event)
          || LumenContainerOpener.tryOpen(stack, event)) {
        return true;
      }
    }
    if (event.key() == GLFW.GLFW_KEY_ESCAPE || this.minecraft.options.keyInventory.matches(event)) {
      onClose();
      return true;
    }
    return false;
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }

  private static ChestMenu createMenu(List<ItemStack> items, Inventory playerInventory) {
    int rows = Math.clamp((items.size() + COLUMNS - 1) / COLUMNS, 1, MAX_ROWS);
    Container container = new SimpleContainer(rows * COLUMNS);
    for (int index = 0; index < Math.min(items.size(), container.getContainerSize()); index++) {
      container.setItem(index, items.get(index));
    }
    return new ChestMenu(menuType(rows), 0, playerInventory, container, rows);
  }

  private static MenuType<ChestMenu> menuType(int rows) {
    return switch (rows) {
      case 1 -> MenuType.GENERIC_9x1;
      case 2 -> MenuType.GENERIC_9x2;
      case 3 -> MenuType.GENERIC_9x3;
      case 4 -> MenuType.GENERIC_9x4;
      case 5 -> MenuType.GENERIC_9x5;
      case 6 -> MenuType.GENERIC_9x6;
      default -> throw new IllegalArgumentException("Unsupported row count: " + rows);
    };
  }
}
