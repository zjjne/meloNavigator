package com.goteny.melo.navigator.demo.page;

import android.os.Bundle;

import com.goteny.melo.navigator.PageListener;
import com.goteny.melo.navigator.annotation.ActivityAnno;
import com.goteny.melo.navigator.annotation.Next;
import com.goteny.melo.navigator.demo.activity.AlphaActivity;
import com.goteny.melo.utils.log.LogMelo;



@ActivityAnno(AlphaActivity.class)
public class PageA extends PageListener
{



    //TODO 以后改造，此处可将void改为boolean，为true的话，就拦截这个页面跳转
    @Next(PageB.class)
    public void onDone(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }

    @Next(PageD.class)
    public void onNext(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }

    public void onSkip(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }




    //不能有静态函数，否则会出错
//    public static void onFinish(Bundle bundle)
//    {
//        LogMelo.d();
//    }
}
