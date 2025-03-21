package com.mars.expcounter;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ExpCounter {
    public ExpCounter(IEventBus eventBus) {
        CommonClass.init();
    }
}
