package name.uwu.feytox.feytweaks.mixin.accessors;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SignBlockEntity.class)
public interface SignBlockEntityAccessor {
    @Accessor
    Text[] getTexts();

    @Accessor
    Text[] getFilteredTexts();

    @Mutable
    @Accessor
    void setTexts(Text[] texts);

    @Mutable
    @Accessor
    void setFilteredTexts(Text[] filteredTexts);
}
