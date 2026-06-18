package com.mars.expcounter.mixin;

import com.mars.expcounter.ExpCounterConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import net.minecraft.client.gui.contextualbar.ExperienceBar;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceBar.class)
public abstract class MixinMinecraft implements ContextualBar {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "extractBackground", at = @At("RETURN"))
    public void renderExperienceBar(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        LocalPlayer localPlayer = this.minecraft.player;
        Font font = this.minecraft.font;

        if(ExpCounterConfig.show_next_level){
            int currentExp = (int) (localPlayer.getXpNeededForNextLevel() * localPlayer.experienceProgress);
            String nextLvlString = currentExp + "/" + localPlayer.getXpNeededForNextLevel();
            int xNextLevel = ((guiGraphics.guiWidth() - font.width(nextLvlString)) / 2) + ExpCounterConfig.position_off_centre_next_level;
            int yNextLevel = guiGraphics.guiHeight() - ExpCounterConfig.position_y_next_level;
            int colNextLevel = 0xFF000000 | (ExpCounterConfig.color_next_level & 0x00FFFFFF);;
            guiGraphics.text(font, nextLvlString, xNextLevel, yNextLevel, colNextLevel, true);
        }
        if(ExpCounterConfig.show_total){
            String totalExperience = String.valueOf(getRealTotalExperience(localPlayer.experienceLevel, (int) (localPlayer.getXpNeededForNextLevel() * localPlayer.experienceProgress)));
            int xTotal = ((guiGraphics.guiWidth() - font.width(totalExperience)) / 2) + ExpCounterConfig.position_off_centre_total;
            int yTotal = guiGraphics.guiHeight() - ExpCounterConfig.position_y_total;
            int colTotal = 0xFF000000 | (ExpCounterConfig.color_total & 0x00FFFFFF);
            guiGraphics.text(font, totalExperience, xTotal, yTotal, colTotal, true);
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
