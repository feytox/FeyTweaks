package ru.feytox.feytweaks.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.feytox.feytweaks.client.ClientEvents;

import java.util.function.Function;

import static ru.feytox.feytweaks.Feytweaks.*;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin {

    @Inject(method = "hasGlowingText", at = @At("HEAD"), cancellable = true)
    public void onIsGlowingText(CallbackInfoReturnable<Boolean> cir) {
        if (FTConfig.toggleMod && FTConfig.hideGlow
                && ((SignBlockEntity)(Object) this).getBlockPos().distToCenterSqr(Minecraft.getInstance().player.position()) >= Math.pow(FTConfig.hideGlowDistance, 2)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getRenderMessages", at = @At("HEAD"), cancellable = true)
    public void onUpdateSign(boolean filterText, Function<Component, FormattedCharSequence> textOrderingFunction, CallbackInfoReturnable<FormattedCharSequence[]> cir) {
        if (ClientEvents.shouldHasText(((SignBlockEntity)(Object) this))) {
            FormattedCharSequence[] emptyRows = new FormattedCharSequence[4];
            emptyRows[0] = FormattedCharSequence.EMPTY;
            emptyRows[1] = FormattedCharSequence.EMPTY;
            emptyRows[2] = FormattedCharSequence.EMPTY;
            emptyRows[3] = FormattedCharSequence.EMPTY;

            cir.setReturnValue(emptyRows);
        }
    }
}