package com.mars.expcounter;

import com.mars.deimos.config.DeimosConfig;

import static com.mars.expcounter.Constants.MOD_ID;

public class CommonClass {
    public static void init() {
        DeimosConfig.init(MOD_ID, ExpCounterConfig.class);
    }
}
