package com.ytgld.vows.client;

import com.ytgld.vows.Vows;
import com.ytgld.vows.attributre.VowsAttributes;
import com.ytgld.vows.tool.Handler;
import com.ytgld.vows.tool.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class RenderSoulShield {
    public static void render(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (mc.gui instanceof ForgeGui forgeGui) {
            renderArmorLevel(
                    event.getGuiGraphics(),
                    mc.player,
                    forgeGui.leftHeight + 10
            );
        }
    }

    public static void renderArmorLevel(GuiGraphics graphics, Player player, int leftHeight) {
        if (player != null) {
            if (showAlpha <= 0) {
                return;
            }
            int l = graphics.guiWidth() / 2 - 91;
            renderSoulShield(graphics, player, graphics.guiHeight() - leftHeight + 20, l);
        }
    }

    public static int setLeftHeight(int value, Player player) {
        float i = Handler.getData(player);
        if (i > 0 && showAlpha > 0) {
            return value + 10;
        }
        return value;
    }


    private static float showAlpha = 255;
    public static double lastShield;

    public static int glow;
    public static float aGlow = 1f;
    public static float aGlowMin = 0F;
    public static float aGlowDOLDOWN = 0f;

    private static void renderSoulShield(GuiGraphics guiGraphics, Player player, int y, int x) {
        float i = Handler.getData(player);
        if (i > 0) {
            if (lastShield != i) {
                glow = 20;
                aGlowDOLDOWN = 1;
                aGlow = 1f;
                aGlowMin = 0f;
            }
            if (glow > 0) {
                glow--;
            }
            if (aGlowDOLDOWN > 0) {
                aGlowDOLDOWN -= 0.1f;
                if (aGlowMin < 1) {
                    aGlowMin += 0.1f;
                }
                if (aGlow > 0) {
                    aGlow -= 0.1f;
                }
            }
            int hurtTime = player.hurtTime;
            if (hurtTime > 0) {
                showAlpha = 255;
            }
            if (i >= player.getAttributeValue(VowsAttributes.soulShieldMaxValue.get())) {
                if (hurtTime <= 0) {
                    if (showAlpha > 0) {
                        showAlpha -= 2.5f;
                    }
                }
            }
            int alpha = (int) showAlpha;
            int light = (int) Math.min(aGlowMin * 255, aGlow * 255);
            if (showAlpha < 0) {
                showAlpha = 0;
            }


            ResourceLocation a1 = Vows.fromNamespaceAndPath("textures/hud/soul_shield_1.png");
            ResourceLocation a2 = Vows.fromNamespaceAndPath("textures/hud/soul_shield_2.png");
            ResourceLocation a3 = Vows.fromNamespaceAndPath("textures/hud/soul_shield_3.png");
            ResourceLocation a4 = Vows.fromNamespaceAndPath("textures/hud/soul_shield_4.png");

            int rowSize = 40;
            int slotsPerRow = 10;

            int rowIndex = (int) i / rowSize;
            int baseI = (int) i % rowSize;

            int yyBase = y - 10;

            for (int row = 0; row <= rowIndex; row++) {
                int offsetI = row == rowIndex ? baseI : rowSize;

                int yyOffset = yyBase - row * 5;

                for (int j = 0; j < slotsPerRow; j++) {
                    drawArmor(
                            offsetI,
                            x,
                            guiGraphics,
                            yyOffset,
                            a1, a2, a3, a4,
                            1 + j * 4, 2 + j * 4,
                            3 + j * 4, 4 + j * 4,
                            j,
                            alpha
                    );

                    drawArmor(
                            offsetI,
                            x,
                            guiGraphics,
                            yyOffset,
                            a1, a2, a3, a4,
                            1 + j * 4, 2 + j * 4,
                            3 + j * 4, 4 + j * 4,
                            j,
                            light
                    );
                }
            }
            lastShield = i;
        }
    }


    private static void drawArmor(int i, int x, GuiGraphics guiGraphics, int yy,
                                  ResourceLocation a1,
                                  ResourceLocation a2,
                                  ResourceLocation a3,
                                  ResourceLocation a4,
                                  int aa,
                                  int b,
                                  int c,
                                  int d,
                                  int offset, int alpha
    ) {
        if (offset > 10) {
            offset = 10;
        }
        if (i > 0) {
            int xx = (x) + offset * 8 - 1;
            if (i > aa + 3) {
                new MGuiGraphics.GUI(VRender::getShaderInstanceLive,
                        false).blit(guiGraphics,a1,
                        9 + ((x) + (offset - 1) * 8 - 1), yy,
                        0,0,
                        9,
                        9,
                        9,
                        9,
                        Light.ARGB.color(
                                alpha,255,255,255
                        ));
            }
            if (i == aa) {
                new MGuiGraphics.GUI(VRender::getShaderInstanceLive,
                        false).blit(guiGraphics,a4,
                        xx, yy,
                        0,0,
                        9,
                        9,
                        9,
                        9,
                        Light.ARGB.color(
                                alpha,255,255,255
                        ));

            }
            if (i == b) {
                new MGuiGraphics.GUI(VRender::getShaderInstanceLive,
                        false).blit(guiGraphics,a3,
                        xx, yy,
                        0,0,
                        9,
                        9,
                        9,
                        9,
                        Light.ARGB.color(
                                alpha,255,255,255
                        ));
            }
            if (i == c) {
                new MGuiGraphics.GUI(VRender::getShaderInstanceLive,
                        false).blit(guiGraphics,a2,
                        xx, yy,
                        0,0,
                        9,
                        9,
                        9,
                        9,
                        Light.ARGB.color(
                                alpha,255,255,255
                        ));
            }
            if (i == d) {
                new MGuiGraphics.GUI(VRender::getShaderInstanceLive,
                        false).blit(guiGraphics,a1,
                        xx, yy,
                        0,0,
                        9,
                        9,
                        9,
                        9,
                        Light.ARGB.color(
                                alpha,255,255,255
                        ));
            }
        }
    }
}