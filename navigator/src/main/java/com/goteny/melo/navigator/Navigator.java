package com.goteny.melo.navigator;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.goteny.melo.navigator.util.ActivityUtils;
import com.goteny.melo.navigator.util.AnnotationUtils;
import com.goteny.melo.utils.log.LogMelo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Navigator implements NaviListener, Application.ActivityLifecycleCallbacks
{

    private static Navigator mInstance;
    private List<TreeParent.Node<NodePage>> route;    //路由路线
    private TreeParent<NodePage> tree;
    private Application application;
//    private Map<Class<? extends Activity>, String> routerMap;


    private Navigator(Application application)
    {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
//        routerMap = RouterMapUtil.fetchRouterMap();
        PageProxy.getInstance().setNaviListener(this);
        PageProxy.getInstance().setApplication(application);
        route = new ArrayList<>();
    }

    public static void init(Application application)
    {
        if (mInstance == null)
        {
            mInstance = new Navigator(application);
        }
    }


    public static void initTree(TreeParent<NodePage> tree)
    {
        mInstance.tree = tree;

        //TODO 这里要改，看看怎么获取第一个页面的node
        mInstance.route.add(mInstance.tree.root());
    }


    /**
     *
     * 注入PageListener成员变量
     * @param activity 将PageListener注入到页面的PageListener对象
     * @return 注入结果
     */
    public static boolean injectPage(Activity activity)
    {
        //TODO 这里要改，看看怎么获取第一个页面的node
//        mInstance.currentNode = mInstance.tree.root();


        Field[] fields = activity.getClass().getDeclaredFields();
        Field targetField = null;

        for (Field field : fields)
        {
            Class fieldType = field.getType();

            //判断成员变量的类型是否继承或实现自PageListener
            if (PageListener.class.isAssignableFrom(fieldType))
            {
                targetField = field;
                break;
            }
        }

        //activity没有添加集成实现自PageListener的成员变量，就不进行后续的对象注入
        if (targetField == null)
            return false;

        Class<? extends PageListener> pageClass = AnnotationUtils.getPageByActivity(activity.getClass());

        if (pageClass == null)
            throw new RuntimeException("必须要添加PageAnno注解且值不能为空，才可调用，注册的对象为:" + pageClass);

        try
        {
            PageListener pageListener = PageProxy.getInstance().create(pageClass);


            if (mInstance.route.size() > 0)
            {
                pageListener.node = mInstance.route.get(mInstance.route.size() - 1);
            }


            LogMelo.d("Page: " + pageListener);

            targetField.setAccessible(true);
            targetField.set(activity, pageListener);

            return true;
        } catch (Throwable e)
        {
            e.printStackTrace();
            return false;
        }
    }


    private void removeRouteItem(Activity activity)
    {
        Field[] fields = activity.getClass().getDeclaredFields();
        Field targetField = null;

        for (Field field : fields)
        {
            Class fieldType = field.getType();

            //判断成员变量的类型是否继承或实现自PageListener
            if (PageListener.class.isAssignableFrom(fieldType))
            {
                targetField = field;
                break;
            }
        }

        //activity有添加集成实现自PageListener的成员变量
        if (targetField != null)
        {
            try
            {
                targetField.setAccessible(true);
                PageListener pageListener = (PageListener) targetField.get(activity);
                TreeParent.Node<NodePage> node = pageListener.node;
                route.remove(node);
            } catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(ActionNext actionNext)
    {
        LogMelo.d("onNext()");

        Class<? extends Activity> targetActivity = getChildActivityClassFromTree(actionNext);

        LogMelo.d(targetActivity + "; " + actionNext.nextPage + "; " + actionNext.bundle);

        if (targetActivity != null)
        {
            Intent intent = new Intent(application, targetActivity);
            intent.putExtras(actionNext.bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);

//                String path = routerMap.get(targetActivity);
//
//                if (!TextUtils.isEmpty(path))
//                {
//                    //启动对应的Activity
//                    RouterUtil.getRouter().build(path).with(bundle).navigation();
//                }
        }

    }

    @Override
    public void onFinish(Class<? extends PageListener> finishPage)
    {
        closePage(finishPage);
    }

    private Class<? extends Activity> getChildActivityClassFromTree(ActionNext actionNext)
    {
        if (route.size() <= 0)
            return null;

        TreeParent.Node<NodePage> currentNode = route.get(route.size() - 1);

        //获取当前节点的所有子节点
        List<TreeParent.Node<NodePage>> childNodes = mInstance.tree.children(currentNode);

        for (TreeParent.Node<NodePage> node : childNodes)
        {

            //通过Action动作名来确定对应的子节点
            if (node.data.bounAction.equals(actionNext.action))
            {
                Class<? extends Activity> activityClass = AnnotationUtils.getActivityByPage(node.data.pageClass);

                if (activityClass == null)
                    throw new RuntimeException("必须要添加ActivityAnno注解且值不能为空，才可调用，注册的对象为:" + node.data.pageClass);

                route.add(node);

                return activityClass;
            }
        }

        return null;
    }

    private void closePage(Class<? extends PageListener> pageClass)
    {
        Class<? extends Activity> activityClass = AnnotationUtils.getActivityByPage(pageClass);
        closeActivity(activityClass);
    }


    private void closeActivity(Class<? extends Activity> activityClass)
    {
        //关闭页面直接用从activity栈里取得的activity来finish
        ActivityUtils.finishActivity(application, activityClass);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        injectPage(activity);
    }

    @Override
    public void onActivityStarted(Activity activity)
    {

    }

    @Override
    public void onActivityResumed(Activity activity)
    {

    }

    @Override
    public void onActivityPaused(Activity activity)
    {

    }

    @Override
    public void onActivityStopped(Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        removeRouteItem(activity);
    }
}
