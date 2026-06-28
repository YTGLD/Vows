#version 330
#moj_import <minecraft:globals.glsl>

uniform sampler2D Sampler0;

in vec2 texCoord0;
in vec4 vertexColor;
in vec3 PositionPosition;
out vec4 fragColor;

void main() {
    vec2 uv = texCoord0;
    float wave1 = sin(uv.y * 15.0 + GameTime * 1800) * 0.032;
    float wave2 = cos(uv.x * 20.0 + GameTime * 1800 * 1.3) * 0.0215;
    float wave3 = sin((uv.x + uv.y) * 30.0 + GameTime * 1800 * 0.7) * 0.021;
    vec2 offset = vec2(wave1 + wave3, wave2 + wave3);
    vec2 deformedUV = uv + offset;
    deformedUV = clamp(deformedUV, 0.0, 1.0);
    vec4 color = texture(Sampler0, deformedUV) * vertexColor;
    if (color.a <= 0.0) discard;
    fragColor = color;
}