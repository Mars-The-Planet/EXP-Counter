package com.mars.expcounter;

import net.fabricmc.api.ModInitializer;

public class ExpCounter implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
    }
}
