package com.goteny.melo.navigator.demo.page;

import android.os.Bundle;

import com.goteny.melo.navigator.PageListener;
import com.goteny.melo.navigator.annotation.ActivityAnno;
import com.goteny.melo.navigator.demo.activity.BetaActivity;
import com.goteny.melo.utils.log.LogMelo;

@ActivityAnno(BetaActivity.class)
public class PageB implements PageListener
{
    public void onDone(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }

    public void onNext(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }
}
