package com.panpiotr.console;

import android.widget.Button;

/**
 * Created by Pan Piotr on 21/03/2015.
 */
public class ParalellButtonRead implements Runnable
{

    AndroidConsole m_console;
    Button m_target;
    ParalellButtonRead(Button target, AndroidConsole console)
    {
        m_console = console;
        m_target = target;
    }
    @Override
    public void run()
    {
        String str = m_console.waitLine();
        m_console.writeLine(str);
        //m_target.setText(str);
    }
    
}
