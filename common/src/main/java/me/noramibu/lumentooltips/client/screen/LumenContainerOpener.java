package me.noramibu.lumentooltips.client.screen;

import me.noramibu.lumentooltips.config.LumenConfig;
import me.noramibu.lumentooltips.config.LumenConfigManager;
import me.noramibu.lumentooltips.config.LumenInputBinding;
import me.noramibu.lumentooltips.tooltip.preview.LumenContainerContents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class LumenContainerOpener {
  private LumenContainerOpener() {}

  public static boolean tryOpen(ItemStack stack, KeyEvent event) {
    LumenConfig.PreviewConfig previewConfig = LumenConfigManager.current().modules.preview;
    if (!previewConfig.enabled
        || !previewConfig.openContainers
        || !LumenInputBinding.matches(previewConfig.openKey, event)) {
      return false;
    }
    return open(stack);
  }

  static boolean open(ItemStack stack) {
    if (stack == null || stack.isEmpty()) {
      return false;
    }
    Minecraft minecraft = Minecraft.getInstance();
    Inventory playerInventory =
        minecraft.player == null ? null : minecraft.player.getInventory();
    var items = LumenContainerContents.openableItems(stack).orElse(null);
    if (playerInventory == null
        || items == null
        || items.stream().noneMatch(item -> !item.isEmpty())) {
      return false;
    }
    minecraft.setScreen(new LumenContainerScreen(stack.copy(), items, playerInventory));
    return true;
  }
}
