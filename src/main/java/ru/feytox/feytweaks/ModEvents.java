package ru.feytox.feytweaks;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Feytweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {

    public static final KeyMapping OPEN_CONFIG_KEY = new KeyMapping("key." + Feytweaks.MOD_ID + ".openConfigKey",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_UNKNOWN,
        "text.autoconfig.feytweaks.title");

    @SubscribeEvent
    public static void initConfig(FMLClientSetupEvent event) {
        registerKeyBinding();
    }

    public static void registerKeyBinding() {
        ClientRegistry.registerKeyBinding(OPEN_CONFIG_KEY);
    }
}