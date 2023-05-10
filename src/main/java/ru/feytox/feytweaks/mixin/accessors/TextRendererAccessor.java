package ru.feytox.feytweaks.mixin.accessors;

import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Font.class)
public interface TextRendererAccessor {
    @Invoker
    static int callAdjustColor(int argb) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    float callRenderText(FormattedCharSequence text, float x, float y, int color, boolean shadow, Matrix4f matrix, MultiBufferSource vertexConsumerProvider, boolean layerType, int underlineColor, int light);
}