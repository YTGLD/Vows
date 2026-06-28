#version 330
#moj_import <minecraft:globals.glsl>

uniform sampler2D Sampler0;

in vec2 texCoord0;
in vec4 vertexColor;
in vec3 PositionPosition;
out vec4 fragColor;

void main()
{
    vec2 uv = texCoord0;
    float t = GameTime * (1000.0 + PositionPosition.x);
    vec2 offset;

    offset.x =
        sin(uv.y * 10.0 + t) * 0.02;

    offset.y =
        cos(uv.x * 10.0 + t * 1.2) * 0.02;

    vec4 color =
        texture(Sampler0, uv + offset)
        * vertexColor;

    fragColor = color;
}