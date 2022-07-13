package name.uwu.feytox.feytweaks.client;

import name.uwu.feytox.feytweaks.mixin.accessors.WorldRendererAccessor;
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
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class FeytweaksClient implements ClientModInitializer {

    public static final String MOD_ID = "feytweaks";

    private static Entity lastTarget = null;

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

    public static boolean isOnScreen(Entity entity) {
        return shouldRender(entity, ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getFrustum());
    }

    public static boolean isOnScreen(BlockEntity blockEntity) {
        return shouldRender(blockEntity, ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getFrustum());
    }

    public static boolean shouldRender(Entity entity, Frustum frustum) {
        Box box = entity.getVisibilityBoundingBox().expand(0.5);
        if (box.isValid() || box.getAverageSideLength() == 0.0) {
            box = new Box(entity.getX() - 2.0, entity.getY() - 2.0, entity.getZ() - 2.0, entity.getX() + 2.0, entity.getY() + 2.0, entity.getZ() + 2.0);
        }
        return frustum.isVisible(box);
    }

    public static boolean shouldRender(BlockEntity blockEntity, Frustum frustum) {
        BlockPos blockPos = blockEntity.getPos();
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
