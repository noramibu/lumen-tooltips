package me.noramibu.lumentooltips;

import me.noramibu.lumentooltips.config.LumenConfigManager;
import java.nio.file.Path;

public final class LumenTooltips {
  public static final String MOD_ID = "lumen_tooltips";

  private LumenTooltips() {}

  public static void init(Path configDirectory) {
    LumenConfigManager.load(configDirectory);
  }
}
