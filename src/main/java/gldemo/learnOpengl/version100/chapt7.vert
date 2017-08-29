uniform mat4 projTrans;

attribute vec2 Position;
attribute vec2 TexCoord;

varying vec2 vTexCoord;

void main() {
    vTexCoord = TexCoord;
    gl_Position = projTrans * vec4(Position, 0.0, 1.0);
}