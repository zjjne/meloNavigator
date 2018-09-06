package com.goteny.melo.navigator.util;

import android.app.Activity;
import android.app.Application;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ActivityUtils
{
    public static List<Activity> getActivitiesStack(Application application)
    {
        List<Activity> list = new ArrayList<>();
        try
        {
            Class<Application> applicationClass = Application.class;
            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(application);

            Class<?> mLoadedApkClass = mLoadedApk.getClass();
            Field mActivityThreadField = mLoadedApkClass.getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            Object mActivityThread = mActivityThreadField.get(mLoadedApk);

            Class<?> mActivityThreadClass = mActivityThread.getClass();
            Field mActivitiesField = mActivityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(mActivityThread);

            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
            if (mActivities instanceof Map)
            {
                @SuppressWarnings("unchecked")
                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
                for (Map.Entry<Object, Object> entry : arrayMap.entrySet())
                {
                    Object value = entry.getValue();
                    Class<?> activityClientRecordClass = value.getClass();
                    Field activityField = activityClientRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Object o = activityField.get(value);

                    list.add((Activity) o);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            list = null;
        }
        return list;
    }


    public static boolean finishActivity(Application application, Class<? extends Activity> activityClass)
    {
        if (activityClass == null)
            return false;

        List<Class<? extends Activity>> activityClasses = new ArrayList<>();
        activityClasses.add(activityClass);

        return finishActivities(application, activityClasses);
    }


    public static boolean finishActivities(Application application, List<Class<? extends Activity>> activityClasses)
    {
        boolean result = false;

        if (application == null || activityClasses == null || activityClasses.size() <= 0)
            return result;

        List<Activity> activities = getActivitiesStack(application);

        for (Class clxx : activityClasses)
        {
            for (Activity activity: activities)
            {
                if (activity.getClass().getName().equals(clxx.getName()))
                {
                    activity.finish();
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
