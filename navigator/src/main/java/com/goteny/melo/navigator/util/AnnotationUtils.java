package com.goteny.melo.navigator.util;

import android.app.Activity;

import com.goteny.melo.navigator.PageListener;
import com.goteny.melo.navigator.annotation.ActivityAnno;
import com.goteny.melo.navigator.annotation.PageAnno;

public class AnnotationUtils
{

    public static Class<? extends PageListener> getPageByActivity(Class<? extends Activity> activityClass)
    {
        Class<? extends PageListener> pageClass;

        PageAnno pageAnno = activityClass.getAnnotation(PageAnno.class);

        if (pageAnno == null || pageAnno.value() == null)
            return null;

        pageClass = pageAnno.value();

        return pageClass;
    }

    public static Class<? extends Activity> getActivityByPage(Class<? extends PageListener> pageClass)
    {
        Class<? extends Activity> activityClass;

        ActivityAnno activityAnno = pageClass.getAnnotation(ActivityAnno.class);

        if (activityAnno == null || activityAnno.value() == null)
            return null;

        activityClass = activityAnno.value();

        return activityClass;
    }
}
