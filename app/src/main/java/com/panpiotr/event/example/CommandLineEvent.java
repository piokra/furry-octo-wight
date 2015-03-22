package com.panpiotr.event.example;

import  com.panpiotr.event.Event;

/**
 * Created by Pan Piotr on 22/03/2015.
 */
public class CommandLineEvent extends Event
{
    private final String TYPE = "CLE";
    private String m_value;

    public CommandLineEvent(String value)
    {
        m_value = value;
    }

    public String getType()
    {
        return TYPE;
    }
    public String getValue()
    {
        return m_value;
    }
}
