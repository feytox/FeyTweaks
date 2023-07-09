package ru.feytox.feytweaks.client;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.feytox.feytweaks.Feytweaks;
import ru.feytox.feytweaks.mixin.accessors.WorldRendererAccessor;

import static ru.feytox.feytweaks.Feytweaks.FTConfig;
import static ru.feytox.feytweaks.ModEvents.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Feytweaks.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void tick(final TickEvent.ClientTickEvent evt) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player != null && evt.phase == TickEvent.Phase.END && client.isWindowActive() &&
            OPEN_CONFIG_KEY.consumeClick()) {
            client.setScreen(AutoConfig.getConfigScreen(ru.feytox.feytweaks.client.FTConfig.class, null).get());
        }
    }

    public static boolean isOnScreen(BeaconBlockEntity beaconBlockEntity) {
        Frustum frustum = ((WorldRendererAccessor) Minecraft.getInstance().levelRenderer).getCullingFrustum();
        BlockPos blockPos = beaconBlockEntity.getBlockPos();
        AABB box = new AABB(blockPos.getX() - 1.0, blockPos.getY() - 1.0, blockPos.getZ() - 1.0, blockPos.getX() + 1.0, 319 - blockPos.getY(), blockPos.getZ() + 1.0);
        return frustum.isVisible(box);
    }

    public static boolean isOnScreen(BlockEntity blockEntity) {
        return isOnScreen(blockEntity.getBlockPos());
    }

    public static boolean isOnScreen(BlockPos blockPos) {
        Frustum frustum = ((WorldRendererAccessor) Minecraft.getInstance().levelRenderer).getCullingFrustum();
        AABB box = new AABB(blockPos.getX() - 1.0, blockPos.getY() - 1.0, blockPos.getZ() - 1.0, blockPos.getX() + 1.0, blockPos.getY() + 1.0, blockPos.getZ() + 1.0);
        return frustum.isVisible(box);
    }

    public static boolean shouldHasText(SignBlockEntity signBlockEntity) {
        BlockPos pos = signBlockEntity.getBlockPos();
        return FTConfig.toggleMod && FTConfig.hideTexts
            && pos.distToCenterSqr(Minecraft.getInstance().player.position()) > Math.pow(FTConfig.textDistance, 2);
    }

    public static boolean shouldHasBeam(BeaconBlockEntity beaconBlockEntity) {
        BlockPos pos = beaconBlockEntity.getBlockPos();
        return FTConfig.toggleMod && FTConfig.hideBeam
            && squared2dDistanceTo(Vec3.atCenterOf(pos), Minecraft.getInstance().player.position()) > Math.pow(FTConfig.beamDistance, 2);
    }

    private static double squared2dDistanceTo(Vec3 pos1, Vec3 pos2) {
        double dx = pos1.x() - pos2.x();
        double dz = pos1.z() - pos2.z();
        return dx * dx + dz * dz;
    }
}