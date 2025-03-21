package com.mars.expcounter;

import com.mars.deimos.config.DeimosConfig;

public class ExpCounterConfig extends DeimosConfig {
    @Entry public static boolean show_next_level = true;
    @Entry public static boolean show_total = true;
    @Entry public static int color_next_level = 16776960;
    @Entry public static int color_total = 16776960;
    @Entry public static int position_off_centre_next_level = -50;
    @Entry public static int position_y_next_level = 30;
    @Entry public static int position_off_centre_total = 50;
    @Entry public static int position_y_total = 30;

}
