#version 410

uniform dvec4 colour;

out vec4 fragColour;



void main() {
    fragColour = vec4(colour.x, colour.y, colour.z, colour.a);
}