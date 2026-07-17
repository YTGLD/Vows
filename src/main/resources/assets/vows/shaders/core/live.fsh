#version 150
#moj_import <matrix.glsl>

uniform sampler2D Sampler0;

uniform float GameTime;

in vec2 texCoord0;
in vec4 vertexColor;

uniform vec4 ColorModulator;

out vec4 fragColor;

void main() {

    vec2 uv = texCoord0;

    // 时间
    float time = GameTime * 1250.0;

    // 波浪扭曲
    uv.x += sin(uv.y * 20.0 + time) * 0.03;
    uv.y += cos(uv.x * 20.0 + time) * 0.03;

    vec4 color = texture(Sampler0, uv) * vertexColor;
    fragColor = color * ColorModulator;
}