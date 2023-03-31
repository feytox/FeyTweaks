package ru.feytox.feytweaks.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.feytox.feytweaks.client.FTConfig;
import ru.feytox.feytweaks.client.FeytweaksClient;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {

    @Inject(method = "render(Lnet/minecraft/block/entity/SignBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
    at = @At("HEAD"), cancellable = true)
    public void onRender(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        if (!FeytweaksClient.isOnScreen(signBlockEntity) && FTConfig.signCulling && FTConfig.toggleMod) {
            ci.cancel();
        }
    }
}
