package com.goteny.melo.navigator.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.goteny.melo.navigator.Navigator;
import com.goteny.melo.navigator.annotation.PageAnno;
import com.goteny.melo.navigator.demo.page.PageG;
import com.goteny.melo.utils.log.LogMelo;
import com.goteny.melo.navigator.demo.R;

@PageAnno(PageG.class)
public class GammaActivity extends AppCompatActivity implements View.OnClickListener
{

    Button button0;
    Button button1;
    Button button2;
    PageG page;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle() + ":" + getClass().getSimpleName());


        button0 = (Button) findViewById(R.id.btn0);
        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        LogMelo.i(getClass().getSimpleName(), "onCreate()");
    }

    @Override
    public void onClick(View v)
    {
        LogMelo.i(getClass().getSimpleName(), "onClick():" + v.getId());

        Bundle bundle = new Bundle();
        bundle.putString("from", getClass().getSimpleName());
        LogMelo.d("" + bundle);

        switch (v.getId())
        {
            case R.id.btn0:
                page.onDone(bundle);
                break;
            case R.id.btn1:
                page.onSkip(bundle);
                break;
            case R.id.btn2:
                Toast.makeText(this, "没东西", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
