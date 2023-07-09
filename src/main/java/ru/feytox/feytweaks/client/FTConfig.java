package ru.feytox.feytweaks.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import ru.feytox.feytweaks.Feytweaks;

@Config(name = Feytweaks.MOD_ID)
public class FTConfig implements ConfigData {

    public boolean toggleMod = true;

    @Comment
    public Comment signs;

    public boolean hideTexts = true;

    public double textDistance = 5;

    public boolean hideGlow = false;

    @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
    public double hideGlowDistance = 5;

    public boolean signCulling = true;

    public boolean simpleGlow = true;

    public boolean glowToShadow = false;

    public boolean fastGlowToShadow = false;

    public boolean optimizeGlow = false;

    @Comment
    public Comment beacons;

    public boolean hideBeam = false;

    public double beamDistance = 15;

    public boolean beaconCulling = true;

    public boolean optimizeBeam = true;
}