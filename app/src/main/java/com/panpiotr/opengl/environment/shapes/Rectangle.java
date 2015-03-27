package com.panpiotr.opengl.environment.shapes;

import android.opengl.GLES20;
import android.util.Log;

import com.panpiotr.opengl.environment.MyOpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Pan Piotr on 24/03/2015.
 */
public class Rectangle {

    private static final String TAG = "RC";
    private FloatBuffer vertexBuffer;
    private float time=0;
    private final float[] rectangleCoords =
            {
                    -1.0f, 1.0f, 0.0f,
                    -1.0f, -1.0f, 0.0f,
                    1.0f, -1.0f, 0.0f,
                    -1.0f, 1.0f, 0.0f,
                    1.0f, -1.0f, 0.0f,
                    1.0f, 1.0f, 0.0f
            };
    private int mProgram;

    public Rectangle()
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(rectangleCoords.length * 4);

        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();

        vertexBuffer.put(rectangleCoords);

        vertexBuffer.position(0);
        int vertexShader = MyOpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                MyOpenGLRenderer.readFile("rectangle_vert"));
        int fragmentShader = MyOpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                MyOpenGLRenderer.readFile("rectangle_frag"));

        int[] status = new int[1];
        GLES20.glGetShaderiv(vertexShader,GLES20.GL_COMPILE_STATUS,status,0);
        if(status[0]!=GLES20.GL_TRUE)
            Log.e(TAG,"Vertex shader did not compile");
        GLES20.glGetShaderiv(fragmentShader,GLES20.GL_COMPILE_STATUS,status,0);
        if(status[0]!=GLES20.GL_TRUE)
            Log.e(TAG,"Fragment shader did not compile");


        mProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(mProgram, vertexShader);

        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);
        time+=0.1;
    }
    public void draw()
    {
        int mPositionHandle;
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        int timeHandle;
        timeHandle = GLES20.glGetUniformLocation(mProgram, "vTime");
        GLES20.glUniform1f(timeHandle,time);
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                12, vertexBuffer);


        double dtime = time;
        float[] rotation =
                {
                        (float)Math.cos(dtime), (float)-Math.sin(dtime), 0,0,
                        (float)Math.sin(dtime), (float)Math.cos(dtime), 0, 0,
                        0, 0, 1.0f, 0,
                        0, 0, 0, 1.0f
                };
        int rotHandle = GLES20.glGetUniformLocation(mProgram, "vRot");
        GLES20.glUniformMatrix4fv(rotHandle,1,true,rotation,0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);


        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        time+=0.01*12;
        if(time>31.4*12.f)
            time=-31.4f*12.f;
    }
}
