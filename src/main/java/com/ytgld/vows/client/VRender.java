package com.ytgld.vows.client;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.ytgld.vows.Vows;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

import static com.mojang.blaze3d.platform.BlendFactor.*;
import static net.minecraft.client.renderer.RenderPipelines.*;

public class VRender  {

    private static final ColorTargetState vColorTargetState = new ColorTargetState(new BlendFunction(SRC_ALPHA, ONE, ONE, ZERO));

    public static final RenderPipeline renderPipelineBlack =
            (RenderPipeline.builder(RenderPipeline.builder(GLOBALS_SNIPPET).
                            withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION).
                            withVertexShader(Vows.fromNamespaceAndPath("core/position_tex_color_black"))
                            .withFragmentShader(Vows.fromNamespaceAndPath("core/position_tex_color_black"))
                            .withBindGroupLayout(BindGroupLayouts.SAMPLER0).withColorTargetState(vColorTargetState)
                            .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR).withPrimitiveTopology(PrimitiveTopology.QUADS).buildSnippet()).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                    .withDepthStencilState(DepthStencilState.DEFAULT)
                    .withLocation(Vows.fromNamespaceAndPath("pipeline/position_tex_color_black")).build());

    public static final RenderPipeline renderPipeline =RenderPipeline.builder(GUI_TEXTURED_SNIPPET).
            withLocation("pipeline/gui_textured").withColorTargetState(vColorTargetState).build();

    public static final RenderPipeline renderPipelineLive =
            (RenderPipeline.builder(RenderPipeline.builder(GLOBALS_SNIPPET).
                            withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION).
                            withVertexShader(Vows.fromNamespaceAndPath("core/live"))
                            .withFragmentShader(Vows.fromNamespaceAndPath("core/live"))
                            .withBindGroupLayout(BindGroupLayouts.SAMPLER0).withColorTargetState(vColorTargetState)
                            .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR).withPrimitiveTopology(PrimitiveTopology.QUADS).buildSnippet()
                    ).withColorTargetState(vColorTargetState)
                    .withDepthStencilState(DepthStencilState.DEFAULT)
                    .withLocation(Vows.fromNamespaceAndPath("pipeline/live")).build());

    public static final RenderPipeline renderPipelineLiveLow =
            (RenderPipeline.builder(RenderPipeline.builder(GLOBALS_SNIPPET).
                            withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION).
                            withVertexShader(Vows.fromNamespaceAndPath("core/live_low"))
                            .withFragmentShader(Vows.fromNamespaceAndPath("core/live_low"))
                            .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
                            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                            .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR).withPrimitiveTopology(PrimitiveTopology.QUADS).buildSnippet()
                    ).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                    .withDepthStencilState(DepthStencilState.DEFAULT)
                    .withLocation(Vows.fromNamespaceAndPath("pipeline/live_low")).build());

    private static final RenderPipeline itemLive = RenderPipeline.builder(
            RenderPipeline.builder(RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET).
                    withVertexShader(
                            Vows.fromNamespaceAndPath("core/item"))
                            .withFragmentShader(Vows.fromNamespaceAndPath("core/item"))
                            .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER1_SAMPLER2)
                            .withCull(false)
                    .withVertexBinding(0, DefaultVertexFormat.ENTITY).withPrimitiveTopology(PrimitiveTopology.QUADS).
                    withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false)).withColorTargetState(vColorTargetState).buildSnippet())
            .withLocation(Vows.fromNamespaceAndPath("pipeline/item_live"))
                    .buildSnippet()).withLocation(Vows.fromNamespaceAndPath("pipeline/item_live")).withShaderDefine("ALPHA_CUTOUT", 0.1F).build();

    public static final RenderPipeline TRANSLUCENT_PARTICLE =
            RenderPipeline.builder(
                            RenderPipeline.builder(MATRICES_FOG_SNIPPET)
                                    .withVertexShader(Vows.fromNamespaceAndPath("core/particle"))
                                    .withFragmentShader(Vows.fromNamespaceAndPath("core/particle"))
                                    .withCull(false)
                                    .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER2)
                                    .withVertexBinding(0, DefaultVertexFormat.PARTICLE)
                                    .withPrimitiveTopology(PrimitiveTopology.QUADS)
                                    .withDepthStencilState(
                                            new DepthStencilState(
                                                    CompareOp.GREATER_THAN_OR_EQUAL,
                                                    false
                                            )
                                    )
                                    .buildSnippet()
                    )
                    .withLocation("pipeline/translucent_particle")
                    .withColorTargetState(
                            new ColorTargetState(
                                    new BlendFunction(
                                            SRC_ALPHA,
                                            ONE,
                                            ONE,
                                            ZERO
                                    )
                            )
                    )
                    .build();
    public static final Function<Identifier, RenderType> renderTypeFunctionLive = Util.memoize((texture) -> {
        RenderSetup state = RenderSetup.builder(itemLive)
                .withTexture("Sampler0", texture)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .createRenderSetup();
        return RenderType.create("vows_item", state);
    });

    private static final RenderPipeline itemLiveNotLight = RenderPipeline.builder(
            RenderPipeline.builder(RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET).
                            withVertexShader(
                                    Vows.fromNamespaceAndPath("core/item_mixin"))
                            .withFragmentShader(Vows.fromNamespaceAndPath("core/item_mixin"))
                            .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER1_SAMPLER2)
                            .withCull(false)
                            .withVertexBinding(0, DefaultVertexFormat.ENTITY)
                            .withPrimitiveTopology(PrimitiveTopology.QUADS).
                            withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
                            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT)).buildSnippet())
                    .withLocation(Vows.fromNamespaceAndPath("pipeline/item_mixin"))
                    .buildSnippet()).withLocation(Vows.fromNamespaceAndPath("pipeline/item_mixin")).withShaderDefine("ALPHA_CUTOUT", 0.1F).build();

    public static final Function<Identifier, RenderType> renderTypeFunctionNotLight = Util.memoize((texture) -> {
        RenderSetup state = RenderSetup.builder(itemLiveNotLight)
                .withTexture("Sampler0", texture)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .createRenderSetup();
        return RenderType.create("vows_item_mixin", state);
    });
}
