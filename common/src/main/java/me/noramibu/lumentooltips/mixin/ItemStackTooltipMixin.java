package me.noramibu.lumentooltips.mixin;

import java.util.List;
import java.util.Optional;
import me.noramibu.lumentooltips.tooltip.LumenTooltipAppender;
import me.noramibu.lumentooltips.tooltip.preview.LumenTooltipPreview;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStack.class, priority = 1500)
public abstract class ItemStackTooltipMixin {
  @Inject(method = "getTooltipLines", at = @At("RETURN"))
  @SuppressWarnings("DataFlowIssue")
  private void lumenTooltips$appendTooltip(
      Item.TooltipContext context,
      Player player,
      TooltipFlag flag,
      CallbackInfoReturnable<List<Component>> callbackInfo) {
    LumenTooltipAppender.append(
        context, (ItemStack) (Object) this, player, callbackInfo.getReturnValue(), flag);
  }

  @Inject(method = "getTooltipImage", at = @At("HEAD"), cancellable = true)
  private void lumenTooltips$getTooltipImage(
      CallbackInfoReturnable<Optional<TooltipComponent>> callbackInfo) {
    Optional<TooltipComponent> preview = LumenTooltipPreview.create((ItemStack) (Object) this);
    if (preview.isPresent()) {
      callbackInfo.setReturnValue(preview);
    }
  }
}
