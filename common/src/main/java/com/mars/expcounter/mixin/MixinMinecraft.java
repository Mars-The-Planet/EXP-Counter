package com.mars.expcounter.mixin;

import com.mars.expcounter.Constants;
import com.mars.expcounter.ExpCounterConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class MixinMinecraft {
    @Shadow
    @Final
    protected Minecraft minecraft;

    @Shadow public abstract Font getFont();

    @Inject(method = "renderExperienceBar", at = @At("RETURN"))
    public void renderExperienceBar(GuiGraphics guiGraphics, int p_282731_, CallbackInfo ci) {
        if(ExpCounterConfig.show_next_level){
            int currentExp = (int) (this.minecraft.player.getXpNeededForNextLevel() * this.minecraft.player.experienceProgress);
            String nextLvlString = currentExp + "/" + this.minecraft.player.getXpNeededForNextLevel();
            int xNextLevel = ((guiGraphics.guiWidth() - this.getFont().width(nextLvlString)) / 2) + ExpCounterConfig.position_off_centre_next_level;
            int yNextLevel = guiGraphics.guiHeight() - ExpCounterConfig.position_y_next_level;
            guiGraphics.drawString(this.getFont(), nextLvlString, xNextLevel, yNextLevel, ExpCounterConfig.color_next_level, true);
        }
        if(ExpCounterConfig.show_total){
            String totalExperience = String.valueOf(getRealTotalExperience(this.minecraft.player.experienceLevel, (int) (this.minecraft.player.getXpNeededForNextLevel() * this.minecraft.player.experienceProgress)));
            int xTotal = ((guiGraphics.guiWidth() - this.getFont().width(totalExperience)) / 2) + ExpCounterConfig.position_off_centre_total;
            int yTotal = guiGraphics.guiHeight() - ExpCounterConfig.position_y_total;
            guiGraphics.drawString(this.getFont(), totalExperience, xTotal, yTotal, ExpCounterConfig.color_total, true);
        }
    }

    @Unique
    public int getRealTotalExperience(int level, int currentExp) {
        int total;

        if (level <= 16) {
            total = (int)(Math.pow(level, 2) + 6 * level);
        }
        else if (level <= 31) {
            total = (int)(2.5 * Math.pow(level, 2) - 40.5 * level + 360);
        }
        else {
            total = (int)(4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
        }
        return total + currentExp;
    }
}
