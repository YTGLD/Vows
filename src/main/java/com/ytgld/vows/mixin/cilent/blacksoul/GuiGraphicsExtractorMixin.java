package com.ytgld.vows.mixin.cilent.blacksoul;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.vows.client.RenderTooltips;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.items.BaseVows;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2ic;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    @Shadow
    @Final
    private Matrix3x2fStack pose;

    @Shadow
    public abstract int guiWidth();

    @Shadow
    public abstract int guiHeight();

    @Inject(at = @At(value = "HEAD"),method = "item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V", cancellable = true)
    public void renderItem(LivingEntity owner, Level level, ItemStack itemStack, int x, int y, int seed, CallbackInfo ci) {
        if (itemStack.getItem() instanceof BaseVows) {
            ci.cancel();
        }
        GuiGraphicsExtractor guiGraphicsExtractor = (GuiGraphicsExtractor) (Object) this;
        RenderVowsItem.renderItem(guiGraphicsExtractor,pose,itemStack,x,y,seed);
    }
    @Inject(at = @At(value = "RETURN"),method = "tooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/Identifier;Lnet/minecraft/world/item/ItemStack;)V", cancellable = true)
    public void tooltip(Font font, List<ClientTooltipComponent> lines, int xo, int yo, ClientTooltipPositioner positioner, @Nullable Identifier style, ItemStack tooltipStack, CallbackInfo ci) {
        if (tooltipStack.getItem() instanceof BaseVows) {
            GuiGraphicsExtractor guiGraphicsExtractor = (GuiGraphicsExtractor) (Object) this;
            RenderTooltipEvent.Pre preEvent = ClientHooks.onRenderTooltipPre(tooltipStack, guiGraphicsExtractor, xo, yo, this.guiWidth(), this.guiHeight(), lines, font, positioner);
            if (!preEvent.isCanceled()) {
                font = preEvent.getFont();
                xo = preEvent.getX();
                yo = preEvent.getY();
                int textWidth = 0;
                int tempHeight = lines.size() == 1 ? -2 : 0;

                for (ClientTooltipComponent line : lines) {
                    int lineWidth = line.getWidth(font);
                    if (lineWidth > textWidth) {
                        textWidth = lineWidth;
                    }

                    tempHeight += line.getHeight(font);
                }

                Vector2ic positionedTooltip = positioner.positionTooltip(this.guiWidth(), this.guiHeight(), xo, yo, textWidth, tempHeight);
                int x = positionedTooltip.x();
                int y = positionedTooltip.y();
                this.pose.pushMatrix();
                RenderTooltips.AddAttributeTooltipsEvent(guiGraphicsExtractor, tooltipStack, font, lines, x, y);
                this.pose.popMatrix();
            }
        }
    }
}
