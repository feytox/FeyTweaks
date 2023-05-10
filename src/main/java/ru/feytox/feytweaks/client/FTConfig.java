package ru.feytox.feytweaks.client;

import eu.midnightdust.lib.config.MidnightConfig;
import ru.feytox.feytweaks.Feytweaks;

public class FTConfig extends MidnightConfig {
    @Entry
    public static boolean toggleMod = true;

    @Comment
    public static Comment signs;

    @Entry
    public static boolean hideTexts = true;

    @Entry
    public static double textDistance = 5;
    
    @Entry
    public static boolean hideGlow = false;

    @Entry(min=0)
    public static double hideGlowDistance = 5;

    @Entry
    public static boolean signCulling = true;

    @Entry
    public static boolean simpleGlow = true;

    @Entry
    public static boolean glowToShadow = false;

    @Entry
    public static boolean fastGlowToShadow = false;

    @Entry
    public static boolean optimizeGlow = false;

    @Comment
    public static Comment beacons;

    @Entry
    public static boolean hideBeam = false;

    @Entry
    public static double beamDistance = 15;

    @Entry
    public static boolean beaconCulling = true;

    @Entry
    public static boolean optimizeBeam = true;

    public static void init() {
        FTConfig.init(Feytweaks.MOD_ID, FTConfig.class);
    }
}