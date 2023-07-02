package ru.feytox.feytweaks.mixin;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.feytox.feytweaks.client.FTConfig;
import ru.feytox.feytweaks.mixin.accessors.TextRendererAccessor;

@Mixin(Font.class)
public class TextRendererMixin {

    @Inject(method = "drawInBatch8xOutline", at = @At("HEAD"), cancellable = true)
    public void onDrawWithOutline(FormattedCharSequence text, float x, float y, int color, int outlineColor, Matrix4f matrix, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        if ((FTConfig.simpleGlow || FTConfig.fastGlowToShadow) && FTConfig.toggleMod) {
            if (color == DyeColor.BLACK.getTextColor()) {
                color = outlineColor;
            }

            fDrawInternal(text, x, y, color, FTConfig.fastGlowToShadow, matrix, vertexConsumers, light);
            ci.cancel();
        }
    }

    private void fDrawInternal(FormattedCharSequence text, float x, float y, int color, boolean shadow, Matrix4f matrix, MultiBufferSource vertexConsumerProvider, int light) {
        color = (color & -67108864) == 0 ? color | -16777216 : color;
        Matrix4f matrix4f = new Matrix4f(matrix);
        Font textRenderer = ((Font) (Object) this);
        if (shadow) {
            ((TextRendererAccessor) textRenderer).callRenderText(text, x, y, color, true, matrix, vertexConsumerProvider, false, 0, light);
            matrix4f.translate(new Vector3f(0.0F, 0.0F, 0.03F));
        }
        ((TextRendererAccessor) textRenderer).callRenderText(text, x, y, color, false, matrix, vertexConsumerProvider, false, 0, light);
    }

    @ModifyVariable(method = "drawInBatch8xOutline", at = @At("STORE"), ordinal = 5)
    private int getOutlineRenderJ(int j) {
        return FTConfig.glowToShadow ? 1 : j;
    }

    @ModifyVariable(method = "drawInBatch8xOutline", at = @At("STORE"), ordinal = 6)
    private int getOutlineRenderK(int k) {
        return FTConfig.glowToShadow ? 1 : k;
    }

    @Inject(method = "drawInBatch8xOutline", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/util/FormattedCharSequence;accept(Lnet/minecraft/util/FormattedCharSink;)Z",
        ordinal = 1),
        cancellable = true)
    private void optimizeOutline(FormattedCharSequence text, float x, float y, int color, int outlineColor, Matrix4f matrix, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        if (FTConfig.optimizeGlow) {
            Matrix4f matrix4f = new Matrix4f(matrix);
            matrix4f.translate(new Vector3f(0.0F, 0.0F, 0.0051F));

            Font.StringRenderOutput drawer = ((Font) (Object) this).new StringRenderOutput(vertexConsumers, x, y,
                TextRendererAccessor.callAdjustColor(color), false, matrix4f, Font.DisplayMode.NORMAL, light);
            text.accept(drawer);
            drawer.finish(0, x);

            ci.cancel();
        }
    }
}