package com.goteny.melo.navigator.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.goteny.melo.navigator.TreeFactory;
import com.goteny.melo.navigator.Navigator;
import com.goteny.melo.navigator.annotation.PageAnno;
import com.goteny.melo.navigator.demo.R;
import com.goteny.melo.navigator.demo.page.PageA;
import com.goteny.melo.utils.log.LogMelo;


@PageAnno(PageA.class)
public class AlphaActivity extends AppCompatActivity implements View.OnClickListener
{

    Button button0;
    Button button1;
    Button button2;
    PageA page;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LogMelo.i(getClass().getSimpleName(), "onCreate()");

        setContentView(R.layout.activity_main);

        setTitle(getTitle() + ":" + getClass().getSimpleName());

        button0 = (Button) findViewById(R.id.btn0);
        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        Navigator.initTree(new TreeFactory().createTree(PageA.class));

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
                page.onNext(bundle);
                break;
            case R.id.btn2:
                page.onSkip(bundle);
                break;
        }

    }
}
