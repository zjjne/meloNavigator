package com.goteny.melo.navigator.demo.page;

import android.os.Bundle;

import com.goteny.melo.navigator.PageListener;
import com.goteny.melo.navigator.annotation.ActivityAnno;
import com.goteny.melo.navigator.annotation.Finish;
import com.goteny.melo.navigator.annotation.Next;
import com.goteny.melo.navigator.demo.activity.GammaActivity;
import com.goteny.melo.utils.log.LogMelo;


@ActivityAnno(GammaActivity.class)
public class PageG extends PageListener
{

    @Next(PageA.class)
    public void onDone(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }

    public void onSkip(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }
}
