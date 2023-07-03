package ru.feytox.feytweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ru.feytox.feytweaks.SignTextPosition;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin {

    @ModifyReturnValue(method = "getBackText", at = @At("RETURN"))
    private SignText injectFtToBack(SignText original) {
        if (!(original instanceof SignTextPosition signTextPosition)) return original;
        SignBlockEntity it = ((SignBlockEntity) (Object) this);
        signTextPosition.ft_setSignPosition(it.getPos());
        return (SignText) signTextPosition;
    }

    @ModifyReturnValue(method = "getFrontText", at = @At("RETURN"))
    private SignText injectFtToFront(SignText original) {
        if (!(original instanceof SignTextPosition signTextPosition)) return original;
        SignBlockEntity it = ((SignBlockEntity) (Object) this);
        signTextPosition.ft_setSignPosition(it.getPos());
        return (SignText) signTextPosition;
    }
}
