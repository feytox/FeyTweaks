package ru.feytox.feytweaks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import ru.feytox.feytweaks.mixin.accessors.WorldRendererAccessor;

@Environment(EnvType.CLIENT)
public class FeytweaksClient implements ClientModInitializer {

    public static final String MOD_ID = "feytweaks";

    @Override
    public void onInitializeClient() {
        FTConfig.init();

        KeyBinding openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + MOD_ID + ".openConfigKey",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "feytweaks.midnightconfig.title"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfigKey.wasPressed()) {
                client.setScreen(FTConfig.getScreen(client.currentScreen, MOD_ID));
            }
        });
    }

    public static boolean isOnScreen(BeaconBlockEntity beaconBlockEntity) {
        Frustum frustum = ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getFrustum();
        BlockPos blockPos = beaconBlockEntity.getPos();
        Box box = new Box(blockPos.getX() - 1.0, blockPos.getY() - 1.0, blockPos.getZ() - 1.0, blockPos.getX() + 1.0, 319 - blockPos.getY(), blockPos.getZ() + 1.0);
        return frustum.isVisible(box);
    }

    public static boolean isOnScreen(BlockEntity blockEntity) {
        return isOnScreen(blockEntity.getPos());
    }

    public static boolean isOnScreen(BlockPos blockPos) {
        Frustum frustum = ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getFrustum();
        Box box = new Box(blockPos.getX() - 1.0, blockPos.getY() - 1.0, blockPos.getZ() - 1.0, blockPos.getX() + 1.0, blockPos.getY() + 1.0, blockPos.getZ() + 1.0);
        return frustum.isVisible(box);

    }

    public static boolean shouldHasText(SignBlockEntity signBlockEntity) {
        BlockPos pos = signBlockEntity.getPos();
        return FTConfig.toggleMod && FTConfig.hideTexts
                && pos.getSquaredDistance(MinecraftClient.getInstance().player.getPos()) > Math.pow(FTConfig.textDistance, 2);
    }

    public static boolean shouldHasBeam(BeaconBlockEntity beaconBlockEntity) {
        BlockPos pos = beaconBlockEntity.getPos();
        return FTConfig.toggleMod && FTConfig.hideBeam
                && squared2dDistanceTo(Vec3d.ofCenter(pos), MinecraftClient.getInstance().player.getPos()) > Math.pow(FTConfig.beamDistance, 2);
    }

    private static double squared2dDistanceTo(Vec3d pos1, Vec3d pos2) {
        double dx = pos1.getX() - pos2.getX();
        double dz = pos1.getZ() - pos2.getZ();
        return dx * dx + dz * dz;
    }
}
