package com.panpiotr.console;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.Semaphore;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.panpiotr.event.Events;
import com.panpiotr.event.example.CommandLineEvent;

/**
 * Created by Pan Piotr on 21/03/2015.
 *
 * This is the main console class.
 *
 */
public class AndroidConsole
{
    private final int SAVED_STRINGS = 16;
    private final int DISPLAYED_STRINGS = 4;

    private String[] m_strings = new String[SAVED_STRINGS];
    private View m_view;
    private TextView[] m_text_views = new TextView[DISPLAYED_STRINGS];
    private LinearLayout m_layout;
    private ImageView m_background;
    private RelativeLayout m_background_layout;
    private Button m_send_button;
    private EditText m_edittext;
    private LinearLayout m_horizontal_layout;
    private String m_return_string;
    private boolean m_is_return_ready;
    private Events m_event_queue;
    private android.os.Handler m_handler;
    private int m_update_interval = 100;
    private Runnable m_console_updater;

    public AndroidConsole(Context context)
    {
        m_handler = new android.os.Handler();
        m_console_updater = new Runnable()
        {
            @Override
            public void run()
            {
                updateConsole();
                m_handler.postDelayed(m_console_updater,m_update_interval);
            }
        };


        m_event_queue = null;
        m_wait_line_semaphore = new Semaphore(1);
        m_read_line_semaphore = new Semaphore(1);
        m_send_line_semaphore = new Semaphore(1);
        m_is_return_ready = false;
        m_background_layout = new RelativeLayout(context);
        m_layout = new LinearLayout(context);
        m_layout.setOrientation(LinearLayout.VERTICAL);
        m_background = new ImageView(context);
        m_horizontal_layout = new LinearLayout(context);
        m_edittext = new EditText(context);
        m_send_button = new Button(context);
        m_send_button.setOnClickListener(new OnButtonClick(m_edittext,this));
        m_background.setImageResource(R.drawable.bg);

        m_edittext.setHint("Hello");
        m_send_button.setHint("Send");

        for(int i=0; i<DISPLAYED_STRINGS; i++)
        {
            m_text_views[i] = new TextView(context);
            m_text_views[i].setText(String.valueOf(i));
            m_layout.addView(m_text_views[i]);

        }

        m_horizontal_layout.setMinimumWidth(100000);
        m_horizontal_layout.addView(m_edittext);
        m_horizontal_layout.addView(m_send_button);
        m_layout.addView(m_horizontal_layout);

        LinearLayout.LayoutParams param_text = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        LinearLayout.LayoutParams param_but = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 0.3f);

        m_send_button.setLayoutParams(param_text);
        m_edittext.setLayoutParams(param_but);
        m_background_layout.setBackgroundColor(Color.rgb(125, 125, 125));
        m_background_layout.addView(m_layout);
        m_background_layout.setMinimumWidth(100000);

        m_view = m_background_layout;



    }


    public AndroidConsole(Context context, Events event_queue)
    {
        this(context);
        m_event_queue=event_queue;
    }


    public View getView()
    {
        return m_view;
    }

    public void writeLine(String str)
    {
        System.arraycopy(m_strings,0,m_strings,1,SAVED_STRINGS-1);
        m_strings[0]=str;

    }
    protected void updateConsole()
    {

        for(int i=0; i<DISPLAYED_STRINGS; i++)
        {
            m_text_views[i].setText(m_strings[i]);
        }

    }
    private Semaphore m_read_line_semaphore;
    public String readLine()
    {

        try {
            m_read_line_semaphore.acquire();
        } catch (InterruptedException e){}

        String copy;
        if(!m_is_return_ready)
            return null;
        copy=m_return_string;
        m_is_return_ready = false;
        m_read_line_semaphore.release();
        return copy;

    }
    private Semaphore m_wait_line_semaphore;
    @RequiresSeparateThread
    public String waitLine()
    {

        try {
            m_wait_line_semaphore.acquire();
        } catch (InterruptedException e){}
        String copy;
        m_is_return_ready = false;

        while(!m_is_return_ready);
        copy = m_return_string;
        m_is_return_ready = false;
        m_wait_line_semaphore.release();
        return copy;

    }
    Semaphore m_send_line_semaphore;
    protected void sendLine(String str)
    {
        try
        {
            m_send_line_semaphore.acquire();
        } catch ( InterruptedException e) { }
        m_return_string = str;
        m_is_return_ready = true;
        if(m_event_queue!=null)
            m_event_queue.offerEvent(new CommandLineEvent(str));
        m_send_line_semaphore.release();
    }

    boolean m_shown = true;
    public void showConsole()
    {
        m_shown=true;
        m_view.setVisibility(View.VISIBLE);
    }
    public void hideConsole()
    {
        m_shown=false;
        m_view.setVisibility(View.INVISIBLE);
    }
    public void toggleConsole()
    {
        if(m_shown)
        {
            hideConsole();
        }
        else
        {
            showConsole();
        }
    }

    public void stopUpdating()
    {
        m_handler.removeCallbacks(m_console_updater);
    }

    public void startUpdating()
    {
        stopUpdating();
        m_console_updater.run();
    }



}
