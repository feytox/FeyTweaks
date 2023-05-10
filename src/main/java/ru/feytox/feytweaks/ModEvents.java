package ru.feytox.feytweaks;

import com.mojang.blaze3d.platform.InputConstants;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import ru.feytox.feytweaks.client.FTConfig;

@Mod.EventBusSubscriber(modid = Feytweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {

    public static final KeyMapping OPEN_CONFIG_KEY = new KeyMapping("key." + Feytweaks.MOD_ID + ".openConfigKey",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_UNKNOWN,
        "feytweaks.midnightconfig.title");

    @SubscribeEvent
    public static void initConfig(FMLClientSetupEvent event) {
        FTConfig.init();
    }

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(OPEN_CONFIG_KEY);
    }
}