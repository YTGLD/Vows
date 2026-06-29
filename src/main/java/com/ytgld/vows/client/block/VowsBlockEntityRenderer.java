package com.ytgld.vows.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.vows.Vows;
import com.ytgld.vows.VowsClient;
import com.ytgld.vows.block.base.VowsBlockEntity;
import com.ytgld.vows.client.RenderVowsItem;
import com.ytgld.vows.client.VRender;
import com.ytgld.vows.items.BaseVows;
import com.ytgld.vows.tool.Light;
import com.ytgld.vows.tool.PlayerDataHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.jspecify.annotations.Nullable;

import java.util.Set;

public class VowsBlockEntityRenderer implements BlockEntityRenderer<VowsBlockEntity,VowsBlockRendererState> {

    private final BlockEntityRendererProvider.Context context;

    public VowsBlockEntityRenderer(BlockEntityRendererProvider.Context context){
        this.context = context;
    }
    @Override
    public VowsBlockRendererState createRenderState() {
        return new VowsBlockRendererState();
    }

    @Override
    public void extractRenderState(VowsBlockEntity blockEntity, VowsBlockRendererState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.blockEntity = blockEntity;
        state.partialTicks = partialTicks;
    }
    @Override
    public void submit(VowsBlockRendererState vowsBlockRendererState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        VowsBlockEntity blockEntity  = vowsBlockRendererState.blockEntity;
        String name = blockEntity.getData(PlayerDataHandler.trueVowsBlock);
        float partialTickTime = vowsBlockRendererState.partialTicks;
        Camera camera = Minecraft.getInstance().gameRenderer.mainCamera();
        Quaternionf rotation = new Quaternionf();
        SingleQuadParticle.FacingCameraMode.LOOKAT_XYZ.setRotation(rotation,camera ,partialTickTime);
        rotation.rotateZ(Mth.lerp(vowsBlockRendererState.partialTicks, 0, 0));
        Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(name));
        if (item instanceof BaseVows baseVows) {

            poseStack.pushPose();
            poseStack.translate(0.5f,2f,0.5f);
            poseStack.mulPose(rotation);
            for (RenderVowsItem.ColorAndImage colorAndImage : baseVows.colorAndImage()){
                int as = (colorAndImage.color() >> 24) & 0xFF;
                int rs = (colorAndImage.color() >> 16) & 0xFF;
                int gs = (colorAndImage.color() >> 8) & 0xFF;
                int bs = colorAndImage.color() & 0xFF;
                render(poseStack,submitNodeCollector,0.75F,VRender.renderTypeFunctionLive.apply(colorAndImage.image()),Light.ARGB.color(blockEntity.getData(PlayerDataHandler.ineAlpha.get()),rs,gs,bs));
            }
            poseStack.popPose();

        }
    }
    private void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector,float size, RenderType renderType,int color){
        poseStack.pushPose();
        submitNodeCollector.submitCustomGeometry(poseStack, renderType,
                (pose, vc) -> {
                    Matrix4f mat = pose.pose();
                    vc.addVertex(mat, -size, -size, 0)
                            .setColor(color)
                            .setUv(0, 1)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, size, -size, 0)
                            .setColor(color)
                            .setUv(1, 1)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, size, size, 0)
                            .setColor(color)
                            .setUv(1, 0)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, -size, size, 0)
                            .setColor(color)
                            .setUv(0, 0)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);
                });
        poseStack.popPose();
    }
}
