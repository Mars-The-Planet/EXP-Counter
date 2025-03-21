package com.mars.expcounter;

import com.mars.deimos.config.DeimosConfig;
import com.mars.expcounter.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

import static com.mars.expcounter.Constants.MOD_ID;

public class CommonClass {
    public static void init() {
        DeimosConfig.init(MOD_ID, ExpCounterConfig.class);
    }
}
