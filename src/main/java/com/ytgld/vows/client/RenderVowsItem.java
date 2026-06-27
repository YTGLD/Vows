package com.ytgld.vows.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.vows.Vows;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.MGuiGraphics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.HashMap;
import java.util.Map;

public class RenderVowsItem {
    private static final Map<Item, AlphaItem> itemStackIntegerHashMap = new HashMap<>();
    private static int time = 0;
    public static void clientTick(ClientTickEvent.Pre event){
        time++;
        for (AlphaItem alphaItem : itemStackIntegerHashMap.values()){
            if (alphaItem.look) {
                if (alphaItem.alpha < 200) {
                    alphaItem.setAlpha(alphaItem.alpha + 40);
                }
                alphaItem.setLook(false);
            }else {
                if (alphaItem.alpha > 0) {
                    alphaItem.setAlpha(alphaItem.alpha - 20);
                    if (alphaItem.alpha < 0) {
                        alphaItem.setAlpha(0);
                    }
                }
            }
        }
    }

    public static void renderItem(GuiGraphics guiGraphics, PoseStack pose, ItemStack stack, int x, int y, int seed) {
        if (stack.getItem() instanceof BaseVows baseVows) {
            if (!itemStackIntegerHashMap.containsKey(baseVows)) {
                itemStackIntegerHashMap.put(baseVows, new AlphaItem(0, true));
            }
            itemStackIntegerHashMap.get(baseVows).setLook(true);
            addBlackLight(guiGraphics, pose, baseVows, x, y, seed);
            for (int i = 0; i < baseVows.colorAndImage().size(); i++) {
                ColorAndImage colorAndImage = baseVows.colorAndImage().get(i);
                ResourceLocation image = colorAndImage.image;
                int color = colorAndImage.color;
                pose.pushPose();
                pose.translate(x, y,0);
                new MGuiGraphics(VRender::getShaderInstanceLive,true)
                        .blit(guiGraphics,image,0,0,0,0,16,16,16,16,color);
                pose.popPose();
            }
        }
    }

    private static void  addBlackLight(GuiGraphics guiGraphics,PoseStack pose, BaseVows baseVows,int x, int y,int seed){
        int a = (int) (itemStackIntegerHashMap.get(baseVows).alpha / 1.5f);
        int r = 20;
        int g = 0;
        int b = 10;
        ResourceLocation base = Vows.fromNamespaceAndPath("textures/gui/black.png");
        float timeBase = time / 75F;
        addCom(32,timeBase, Light.ARGB.color(Math.min(255,a), r, g, b), guiGraphics, pose, base, x, y, seed);
        addCom(32,timeBase,Light.ARGB.color(Math.min(255,a / 2), r, g, b), guiGraphics, pose, base, x, y, seed);
        addCom(32,timeBase,Light.ARGB.color(Math.min(255,a / 4), r, g, b), guiGraphics, pose, base, x, y, seed);

        addCom(32,timeBase,Light.ARGB.color(a, r, g, b), guiGraphics, pose,
                Vows.fromNamespaceAndPath("textures/gui/ci_star.png"), x, y, seed);
        addCom(18,-timeBase * 2  + (float)Math.PI / 2  ,Light.ARGB.color(a, r, g, b), guiGraphics, pose,
                Vows.fromNamespaceAndPath("textures/gui/ci_star.png"), x, y, seed);
        addCom(18,-timeBase * 2  + (float)Math.PI / 8  ,Light.ARGB.color(a, r, g, b), guiGraphics, pose,
                Vows.fromNamespaceAndPath("textures/gui/ci_star.png"), x, y, seed);
    }

    private static void addCom(int size,float time , int color,GuiGraphics guiGraphics,PoseStack pose, ResourceLocation fire, int x, int y,int seed){
        pose.pushPose();
        pose.translate(8,8,0);
        pose.pushPose();
        pose.translate(x, y,0);
        pose.mulPose(Axis.ZN.rotation(time));
        pose.translate(-x, -y,0);
        new MGuiGraphics(VRender::getShaderInstanceHasBlack,false).blit(guiGraphics, fire,
                x - size / 2f, y - size /2f, 0, 0,
                size, size, size, size,
                color);
        pose.popPose();

        pose.popPose();
    }

    public record ColorAndImage(int color , ResourceLocation image){}
    private static class AlphaItem {
        public int alpha;
        public boolean look;
        public AlphaItem(int alpha,boolean look ){
            this.alpha = alpha;
            this.look = look;
        }

        public void setLook(boolean look) {
            this.look = look;
        }

        public void setAlpha(int a){
            this.alpha = a;
        }
    }
}
