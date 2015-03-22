package com.panpiotr.console;

import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.panpiotr.event.Events;
import com.panpiotr.opengl.environment.MyOpenGLSurfaceView;



public class ExampleActivity extends ActionBarActivity {

    private AndroidConsole m_console;
    private GLSurfaceView m_gl_surface;
    private RelativeLayout m_layout;
    private Events m_events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_events = new Events();
        m_layout = new RelativeLayout(this);
        m_gl_surface = new MyOpenGLSurfaceView(this,m_events);
        m_layout.addView(m_gl_surface);
        m_console = new AndroidConsole(this,m_events);
        m_console.writeLine("Witam");
        m_console.writeLine("Pozdrawiam");
        m_console.writeLine("Ryszard");
        m_console.startUpdating();
        Thread t = new Thread(new ProcessingThreadExample(m_console, m_events));
        t.start();
        setContentView(m_layout);
        m_layout.addView(m_console.getView());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.toggle_console) {
            m_console.writeLine("Console possibly toggled");
            m_console.toggleConsole();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeText(View v)
    {
        Button b = (Button)v;
        String str = m_console.readLine();
        if(str!=null)

        b.setText(str);
    }
    public void changeTextParallel(View v)
    {
        Button but = (Button)v;
        Runnable runnable = new ParalellButtonRead(but,m_console);
        Thread thread = new Thread(runnable);
        thread.start();
    }


    private void updateConsole()
    {
        while(true)
        {
            m_console.updateConsole();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

