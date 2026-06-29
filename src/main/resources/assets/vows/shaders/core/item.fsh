#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>


uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in float Time;

out vec4 fragColor;

void main() {
    vec2 uv = texCoord0;
    float wave1 = sin(uv.y * 15.0 + Time * 1000) * 0.02;
    float wave2 = cos(uv.x * 20.0 + Time * 1000 * 1.3) * 0.015;
    float wave3 = sin((uv.x + uv.y) * 30.0 + Time * 1000 * 0.7) * 0.01;
    vec2 offset = vec2(wave1 + wave3, wave2 + wave3);
    vec2 deformedUV = uv + offset;
    deformedUV = clamp(deformedUV, 0.0, 1.0);

    vec4 color = texture(Sampler0, deformedUV);

#ifdef ALPHA_CUTOUT

#endif

    color *= vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;

    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
