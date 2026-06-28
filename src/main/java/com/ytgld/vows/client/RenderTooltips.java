package com.ytgld.vows.client;

import com.ytgld.vows.Vows;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.VowsTooltipRenderUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

public class RenderTooltips {
    public static void AddAttributeTooltipsEvent(GuiGraphicsExtractor guiGraphicsExtractor,ItemStack stack,Font font, List<ClientTooltipComponent> lines, int x, int y){
        if (stack.getItem() instanceof BaseVows) {
            int textWidth = 0;
            int tempHeight = lines.size() == 1 ? -2 : 0;

            for (ClientTooltipComponent line : lines) {
                int lineWidth = line.getWidth(font);
                if (lineWidth > textWidth) {
                    textWidth = lineWidth;
                }

                tempHeight += line.getHeight(font);
            }
            VowsTooltipRenderUtil.extractTooltipBackground(guiGraphicsExtractor, x, y,
                    textWidth, tempHeight,
                    Vows.fromNamespaceAndPath("tooltip/background"),
                    Vows.fromNamespaceAndPath("tooltip/frame"));
        }
    }
}
