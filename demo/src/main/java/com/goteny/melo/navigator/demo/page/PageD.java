package com.goteny.melo.navigator.demo.page;

import android.os.Bundle;

import com.goteny.melo.navigator.PageListener;
import com.goteny.melo.navigator.annotation.ActivityAnno;
import com.goteny.melo.navigator.annotation.Finish;
import com.goteny.melo.navigator.annotation.Next;
import com.goteny.melo.navigator.demo.activity.DeltaActivity;
import com.goteny.melo.utils.log.LogMelo;

@ActivityAnno(DeltaActivity.class)
public class PageD extends PageListener
{
    @Finish
    @Next(PageE.class)
    public void onNext(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }



    @Finish
    public void onFinish()
    {
        LogMelo.d("Page:" + this);
    }
}
