package me.noramibu.lumentooltips.config;

import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

public enum HoldMode {
  ALWAYS,
  KEY,
  ADVANCED,
  SHIFT,
  ALT;

  public String serializedName() {
    return name().toLowerCase(Locale.ROOT);
  }

  public Component displayName() {
    return Component.translatable("config.lumen_tooltips.value.hold." + serializedName());
  }

  public boolean isActive(TooltipFlag flag, String keyName) {
    return switch (this) {
      case ALWAYS -> true;
      case KEY -> LumenInputBinding.isDown(keyName);
      case SHIFT -> Minecraft.getInstance().hasShiftDown();
      case ALT -> Minecraft.getInstance().hasAltDown();
      case ADVANCED -> flag.isAdvanced();
    };
  }

  public static HoldMode byName(String value, HoldMode fallback) {
    try {
      return valueOf(value.toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException exception) {
      return fallback;
    }
  }
}
