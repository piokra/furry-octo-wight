package com.panpiotr.console;

import com.panpiotr.event.Event;
import com.panpiotr.event.Events;

/**
 * Created by Pan Piotr on 22/03/2015.
 */
public class ProcessingThreadExample implements Runnable{

    private AndroidConsole m_console;
    private Events m_events;
    private boolean m_is_done;
    ProcessingThreadExample(AndroidConsole console, Events events)
    {
        m_console=console;
        m_events=events;
        m_is_done=false;
    }

    @Override
    public void run()
    {
        while(!m_is_done)
        {
            processEvents();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){ }
        }
        m_console.writeLine("Terminating event processing thread.");
    }

    public void terminate()
    {
        m_is_done=true;
    }

    private void processEvents()
    {
        Event event=m_events.pollEvent();
        while(event!=null)
        {
            m_console.writeLine("Type: "+event.getType()+" Value: "+event.getValue());
            event=m_events.pollEvent();
        }
    }
}
