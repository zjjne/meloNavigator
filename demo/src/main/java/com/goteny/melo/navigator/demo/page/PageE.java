package com.goteny.melo.navigator.demo.page;

import android.os.Bundle;

import com.goteny.melo.navigator.PageListener;
import com.goteny.melo.navigator.annotation.ActivityAnno;
import com.goteny.melo.navigator.annotation.Next;
import com.goteny.melo.navigator.demo.activity.EpsilonActivity;
import com.goteny.melo.utils.log.LogMelo;

@ActivityAnno(EpsilonActivity.class)
public class PageE extends PageListener
{

    @Next(PageG.class)
    public void onNext(Bundle bundle)
    {
        LogMelo.d("Page:" + this);
    }


}
