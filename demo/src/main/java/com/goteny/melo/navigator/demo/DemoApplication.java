package com.goteny.melo.navigator.demo;

import android.app.Application;

import com.goteny.melo.navigator.Navigator;

public class DemoApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Navigator.init(this);
        Navigator.enableLog(true);
    }
}
