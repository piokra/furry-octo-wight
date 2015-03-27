package com.panpiotr.opengl.environment;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.panpiotr.console.R;
import com.panpiotr.opengl.environment.shapes.Rectangle;
import com.panpiotr.opengl.environment.shapes.Triangle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Pan Piotr on 21/03/2015.
 */
public class MyOpenGLRenderer implements GLSurfaceView.Renderer
{
    private static Context mContext = null;
    Rectangle mRectangle;
    public MyOpenGLRenderer(Context context)
    {

        if(mContext==null)
        mContext=context;



    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        mRectangle = new Rectangle();
        GLES20.glClearColor(1.0f, 0.0f, 0.5f, 1.0f);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);

    }
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    public static String readFile(String filename)
    {
        Log.d("START","DD");
        int resId = mContext.getResources().getIdentifier(filename,"raw",mContext.getPackageName());
        Log.d("RFTAG",String.valueOf(resId));
        InputStream inputStream = mContext.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            Log.d("BLAGAM O LITOSC","JEST LITOSC");
        } catch (IOException e) {
            Log.d("BLAGAM O LITOSC","NIE MA LITOSCI");
            return null;
        }
        return text.toString();
    }
    @Override
    public void onDrawFrame(GL10 gl)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mRectangle.draw();
    }
}
