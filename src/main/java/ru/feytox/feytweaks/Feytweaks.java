package ru.feytox.feytweaks;

import net.fabricmc.api.ModInitializer;
import ru.feytox.feytweaks.client.FTConfig;

public class Feytweaks implements ModInitializer {
    @Override
    public void onInitialize() {
        FTConfig.init();
    }
}
