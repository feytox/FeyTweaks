package name.uwu.feytox.feytweaks.client;

import eu.midnightdust.lib.config.MidnightConfig;

public class FTConfig extends MidnightConfig {
    @Entry
    public static boolean toggleMod = true;

    @Comment
    public static Comment signs;

    @Entry
    public static boolean hideTexts = true;

    @Entry
    public static double textDistance = 10;

    @Entry
    public static boolean signCulling = true;

    @Entry
    public static boolean hideGlow = true;

    @Comment
    public static Comment beacons;

    @Entry
    public static boolean hideBeam = true;

    @Entry
    public static double beamDistance = 15;

    @Entry
    public static boolean beaconCulling = true;

    @Entry
    public static boolean optimizeBeam = true;

    public static void init() {
        FTConfig.init(FeytweaksClient.MOD_ID, FTConfig.class);
    }
}
