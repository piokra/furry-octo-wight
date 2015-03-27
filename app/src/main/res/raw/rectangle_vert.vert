attribute vec4 vPosition;
varying vec4 varpos;
uniform float vTime;
uniform mat4 vRot;
void main()
{
    gl_Position = vPosition;
    varpos = vPosition;
}