package name.uwu.feytox.feytweaks.mixin;

import name.uwu.feytox.feytweaks.client.FTConfig;
import name.uwu.feytox.feytweaks.mixin.accessors.TextRendererAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public class TextRendererMixin {

    @Inject(method = "drawWithOutline", at = @At("HEAD"), cancellable = true)
    public void onDrawWithOutline(OrderedText text, float x, float y, int color, int outlineColor, Matrix4f matrix, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if ((FTConfig.simpleGlow || FTConfig.fastGlowToShadow) && FTConfig.toggleMod) {
            if (color == DyeColor.BLACK.getSignColor()) {
                color = outlineColor;
            }

            TextRenderer textRenderer = ((TextRenderer)(Object) this);
            textRenderer.draw(text, x, y, color, FTConfig.fastGlowToShadow, matrix, vertexConsumers, false,
                    0, light);
            ci.cancel();
        }
    }

    @Inject(method = "drawInternal(Lnet/minecraft/text/OrderedText;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Matrix4f;addToLastColumn(Lnet/minecraft/util/math/Vec3f;)V"),
            cancellable = true)
    private void onDrawInternal(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
        if (FTConfig.fastGlowToShadow && FTConfig.toggleMod) {
            Matrix4f matrix4f = matrix.copy();
            matrix4f.addToLastColumn(new Vec3f(0.0F, 0.0F, 0.000009F));

            x = ((TextRendererAccessor) this).callDrawLayer(text, x, y, color, false, matrix4f, vertexConsumerProvider,
                    false, backgroundColor, light);
            cir.setReturnValue((int) x + (shadow ? 1 : 0));
        }
    }


    @ModifyVariable(method = "drawWithOutline", at = @At("STORE"), ordinal = 5)
    private int getOutlineRenderJ(int j) {
        return FTConfig.glowToShadow ? 1 : j;
    }

    @ModifyVariable(method = "drawWithOutline", at = @At("STORE"), ordinal = 6)
    private int getOutlineRenderK(int k) {
        return FTConfig.glowToShadow ? 1 : k;
    }

    @Inject(method = "drawWithOutline", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/text/OrderedText;accept(Lnet/minecraft/text/CharacterVisitor;)Z",
            ordinal = 1),
            cancellable = true)
    private void optimizeOutline(OrderedText text, float x, float y, int color, int outlineColor, Matrix4f matrix, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (FTConfig.optimizeGlow) {
            Matrix4f matrix4f = matrix.copy();
            matrix4f.addToLastColumn(new Vec3f(0.0F, 0.0F, 0.0051F));

            TextRenderer.Drawer drawer2 = ((TextRenderer) (Object) this).new Drawer(vertexConsumers, x, y,
                    TextRendererAccessor.callTweakTransparency(color), false, matrix4f,
                    false, light);
            text.accept(drawer2);
            drawer2.drawLayer(0, x);

            ci.cancel();
        }
    }
}
