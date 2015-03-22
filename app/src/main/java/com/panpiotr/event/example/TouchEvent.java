package com.panpiotr.event.example;

import com.panpiotr.event.Event;

/**
 * Created by Pan Piotr on 22/03/2015.
 */
public class TouchEvent extends Event {

    public static final String TYPE = "TE";
    float m_dx;
    float m_dy;

    public TouchEvent(float dx, float dy)
    {
        m_dx=dx;
        m_dy=dy;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getValue() {
        return String.valueOf(m_dx)+" "+String.valueOf(m_dy);
    }
}
