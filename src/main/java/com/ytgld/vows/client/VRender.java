package com.ytgld.vows.client;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.ytgld.vows.Vows;
import net.minecraft.client.renderer.BindGroupLayouts;

import static com.mojang.blaze3d.platform.BlendFactor.*;
import static net.minecraft.client.renderer.RenderPipelines.GLOBALS_SNIPPET;

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


}
