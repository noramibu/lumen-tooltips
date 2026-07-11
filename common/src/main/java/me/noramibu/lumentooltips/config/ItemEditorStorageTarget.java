package me.noramibu.lumentooltips.config;

import java.util.Locale;
import net.minecraft.network.chat.Component;

public enum ItemEditorStorageTarget {
  FIRST_AVAILABLE,
  PAGE_NUMBER,
  PAGE_NAME;

  public String serializedName() {
    return name().toLowerCase(Locale.ROOT);
  }

  public Component displayName() {
    return Component.translatable(
        "config.lumen_tooltips.value.item_editor_target." + serializedName());
  }

  public static ItemEditorStorageTarget byName(
      String name, ItemEditorStorageTarget fallback) {
    for (ItemEditorStorageTarget target : values()) {
      if (target.serializedName().equalsIgnoreCase(name)) {
        return target;
      }
    }
    return fallback;
  }
}
