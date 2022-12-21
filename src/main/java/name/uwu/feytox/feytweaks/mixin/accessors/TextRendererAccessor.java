package name.uwu.feytox.feytweaks.mixin.accessors;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TextRenderer.class)
public interface TextRendererAccessor {
    @Invoker
    static int callTweakTransparency(int argb) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    float callDrawLayer(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light);
}
