package com.mars.expcounter.mixin;

import com.mars.expcounter.ExpCounterConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
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

    @Shadow protected int screenWidth;

    @Shadow protected int screenHeight;

    @Inject(method = "renderExperienceBar", at = @At("RETURN"))
    public void renderExperienceBar(PoseStack guiGraphics, int p_282731_, CallbackInfo ci) {
        if(ExpCounterConfig.show_next_level){
            int currentExp = (int) (this.minecraft.player.getXpNeededForNextLevel() * this.minecraft.player.experienceProgress);
            String nextLvlString = currentExp + "/" + this.minecraft.player.getXpNeededForNextLevel();
            int xNextLevel = ((this.screenWidth - this.getFont().width(nextLvlString)) / 2) + ExpCounterConfig.position_off_centre_next_level;
            int yNextLevel = this.screenHeight - ExpCounterConfig.position_y_next_level;
            this.getFont().drawShadow(guiGraphics, nextLvlString, xNextLevel, yNextLevel, ExpCounterConfig.color_next_level);
        }
        if(ExpCounterConfig.show_total){
            String totalExperience = String.valueOf(getRealTotalExperience(this.minecraft.player.experienceLevel, (int) (this.minecraft.player.getXpNeededForNextLevel() * this.minecraft.player.experienceProgress)));
            int xTotal = ((this.screenWidth - this.getFont().width(totalExperience)) / 2) + ExpCounterConfig.position_off_centre_total;
            int yTotal = this.screenHeight - ExpCounterConfig.position_y_total;
            this.getFont().drawShadow(guiGraphics, totalExperience, xTotal, yTotal, ExpCounterConfig.color_total);
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
