package com.panpiotr.opengl.environment;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.panpiotr.event.Events;
import com.panpiotr.event.example.CommandLineEvent;
import com.panpiotr.event.example.TouchEvent;

/**
 * Created by Pan Piotr on 21/03/2015.
 */
public class MyOpenGLSurfaceView extends GLSurfaceView
{
    private final Renderer m_renderer;
    private float m_last_x=0;
    private float m_last_y=0;
    private Events m_events = null;

    public MyOpenGLSurfaceView(Context context)
    {
        super(context);
        m_renderer = new MyOpenGLRenderer();
        setRenderer(m_renderer);

    }
    public MyOpenGLSurfaceView(Context context, Events events)
    {
        this(context);
        m_events=events;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                if(m_events!=null)
                {
                    m_events.offerEvent(new CommandLineEvent("Get your nasty finger off my screen!"));
                }
            }
            case MotionEvent.ACTION_MOVE:
            {
                float x, y;
                x=event.getX();
                y=event.getY();
                if(m_events!=null)
                {
                    m_events.offerEvent(new TouchEvent(m_last_x-x,m_last_y-y));
                }
                m_last_x=x;
                m_last_y=y;

            }
            case MotionEvent.ACTION_UP:
            {

            }
        }
        return true;
    }

}
