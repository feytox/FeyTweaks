package ru.feytox.feytweaks;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraftforge.fml.common.Mod;
import ru.feytox.feytweaks.client.FTConfig;

@Mod(Feytweaks.MOD_ID)
public class Feytweaks {
    public static final String MOD_ID = "feytweaks";

    public static FTConfig FTConfig;

    public Feytweaks() {
        FTConfig = AutoConfig.register(FTConfig.class, GsonConfigSerializer::new).get();
    }

}