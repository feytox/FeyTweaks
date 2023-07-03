package ru.feytox.feytweaks.mixin;

import net.minecraft.block.entity.SignText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.feytox.feytweaks.SignTextPosition;
import ru.feytox.feytweaks.client.FTConfig;
import ru.feytox.feytweaks.client.FeytweaksClient;

import java.util.function.Function;

@Mixin(SignText.class)
public class SignTextMixin implements SignTextPosition {

    @Unique
    @Nullable
    private BlockPos ft_signPosition = null;

    @Inject(method = "isGlowing", at = @At("HEAD"), cancellable = true)
    public void onIsGlowing(CallbackInfoReturnable<Boolean> cir) {
        BlockPos signPos = FeytweaksClient.getSignPos(((SignText)(Object) this));
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (signPos == null || player == null) return;

        if (FTConfig.toggleMod && FTConfig.hideGlow
                && signPos.getSquaredDistance(player.getPos()) >= Math.pow(FTConfig.hideGlowDistance, 2)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getOrderedMessages", at = @At("HEAD"), cancellable = true)
    public void onGetOrderedMessages(boolean filterText, Function<Text, OrderedText> textOrderingFunction, CallbackInfoReturnable<OrderedText[]> cir) {
        if (FeytweaksClient.shouldRenderText(((SignText)(Object) this))) {
            OrderedText[] emptyRows = new OrderedText[4];
            for (int i = 0; i < 4; i++) {
                emptyRows[i] = OrderedText.EMPTY;
            }

            cir.setReturnValue(emptyRows);
        }
    }

    @Override
    public @Nullable BlockPos ft_getSignPosition() {
        return ft_signPosition;
    }

    @Override
    public void ft_setSignPosition(@NotNull BlockPos blockPos) {
        ft_signPosition = blockPos;
    }
}
