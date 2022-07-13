package name.uwu.feytox.feytweaks.mixin.accessors;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SignBlockEntityRenderer.class)
public interface SignBlockEntityRendererAccessor {
    @Accessor
    Map<SignType, SignBlockEntityRenderer.SignModel> getTypeToModel();

    @Accessor
    TextRenderer getTextRenderer();
}
