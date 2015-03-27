precision mediump float;
varying vec4 varpos;
uniform vec4 vColor;
uniform float vTime;
uniform mat4 vRot;

mat4 rotationMatrix(float theta)
{
    return mat4(cos(theta),-sin(theta),0,0,

                sin(theta), cos(theta),0,0,
                0         ,         0, 1,0,
                0         ,         0, 0,1);
}

void main()
{
    vec4 pos = varpos*vRot;
    float x = varpos.x;
    float y = varpos.y;

    if(x*x+y*y<1.0f/4.0f&&x*x+y*y>0.9f/4.0f)
        gl_FragColor = vec4(1,1,1,1);
    else
    if(x*x+y*y<0.9f/4.0f)
    {
        pos=rotationMatrix(x*x+y*y+vTime*0.1)*varpos;
        x=pos.x;
        y=pos.y;
        gl_FragColor = vec4(sin(y/x*0.2f+vTime*0.1),sin(y/x*0.4f+vTime*0.1),sin(y/x*0.8f+vTime*0.1),1);
    }
    else
    {
        pos=varpos*rotationMatrix(x*x+y*y);
        x=pos.x;
        y=pos.y;
        gl_FragColor = vec4(sin(x/y*2.0f+vTime),sin(x/y*3.0f+vTime),sin(x/y*4.0f+vTime),1);

    }
}