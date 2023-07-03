package ru.feytox.feytweaks;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SignTextPosition {
    @Nullable BlockPos ft_getSignPosition();
    void ft_setSignPosition(@NotNull BlockPos blockPos);
}
