package me.noramibu.lumentooltips.fabric;

import me.noramibu.lumentooltips.LumenTooltips;
import me.noramibu.lumentooltips.client.FabricItemEditorApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;

public final class FabricLumenTooltips implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    LumenTooltips.init(FabricLoader.getInstance().getConfigDir());
    FabricLoader.getInstance()
        .getModContainer("itemeditor")
        .filter(mod -> supportsItemEditorApi(mod.getMetadata().getVersion()))
        .ifPresent(mod -> FabricItemEditorApi.install());
  }

  private static boolean supportsItemEditorApi(Version version) {
    try {
      return version.compareTo(Version.parse("26.1-b19")) >= 0;
    } catch (VersionParsingException ignored) {
      return false;
    }
  }
}
