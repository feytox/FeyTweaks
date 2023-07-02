package ru.feytox.feytweaks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.feytox.feytweaks.client.FTConfig;
import ru.feytox.feytweaks.client.ClientEvents;

@Mixin(SignRenderer.class)
public class SignBlockEntityRendererMixin {

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
    at = @At("HEAD"), cancellable = true)
    public void onRender(SignBlockEntity signBlockEntity, float f, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        if (!ClientEvents.isOnScreen(signBlockEntity) && FTConfig.signCulling && FTConfig.toggleMod) {
            ci.cancel();
        }
    }
}