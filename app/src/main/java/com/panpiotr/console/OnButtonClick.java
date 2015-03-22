package com.panpiotr.console;

import android.view.View;
import android.widget.EditText;

import com.panpiotr.console.AndroidConsole;

/**
 * Created by Pan Piotr on 21/03/2015.
 *
 * Listens for the send button clicks
 */
public class OnButtonClick implements View.OnClickListener
{

    private EditText m_text;
    private AndroidConsole m_target;

    public OnButtonClick(EditText text, AndroidConsole target)
    {
        m_text = text;
        m_target = target;
    }

    @Override
    public void onClick(View v)
    {
        m_target.writeLine(m_text.getText().toString());
        m_target.sendLine(m_text.getText().toString());
        m_text.setText("");


    }
}
