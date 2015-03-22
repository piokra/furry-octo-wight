package com.panpiotr.event;


import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Pan Piotr on 22/03/2015.
 */
public class Events {

    private ConcurrentLinkedQueue<Event> m_events;

    public Events()
    {
        m_events = new ConcurrentLinkedQueue<>();

    }

    public Event pollEvent()
    {
        return m_events.poll();
    }
    public boolean offerEvent(Event event)
    {
        return m_events.offer(event);
    }

}
