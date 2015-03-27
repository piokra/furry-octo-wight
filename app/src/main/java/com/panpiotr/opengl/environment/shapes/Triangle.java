package com.panpiotr.opengl.environment.shapes;

import android.opengl.GLES20;
import android.util.FloatMath;

import com.panpiotr.opengl.environment.MyOpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private float time=0;
    private final int m_program;
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "uniform float vTime;" +
                    "void main() {" +
                    "  float red = sin(vTime);" +
                    "  gl_FragColor = vec4(red,1,1,1);" +
                    "}";
    private FloatBuffer vertexBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    public Triangle() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        int vertexShader = MyOpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyOpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        m_program = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(m_program, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(m_program, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(m_program);
    }

    public void draw()
    {

        GLES20.glUseProgram(m_program);
        mPositionHandle = GLES20.glGetAttribLocation(m_program,"vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle,COORDS_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride,vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(m_program, "vColor");
        int timeHandle;
        timeHandle = GLES20.glGetUniformLocation(m_program, "vTime");


        GLES20.glUniform1f(timeHandle,time);
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);


        GLES20.glDisableVertexAttribArray(mPositionHandle);
        time+=0.1;


    }
}