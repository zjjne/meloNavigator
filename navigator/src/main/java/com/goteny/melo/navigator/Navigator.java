package com.goteny.melo.navigator;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.goteny.melo.navigator.annotation.ActivityAnno;
import com.goteny.melo.navigator.annotation.PageAnno;
import com.goteny.melo.utils.log.LogMelo;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class Navigator implements NaviListener
{

    private static Navigator mInstance;
    private TreeParent.Node<String> currentNode;
    private TreeParent<String> tree;
    private Application application;
//    private Map<Class<? extends Activity>, String> routerMap;



    private Navigator()
    {
//        routerMap = RouterMapUtil.fetchRouterMap();
    }

    public static void init(Application application)
    {
        if (mInstance == null)
        {
            mInstance = new Navigator();
            mInstance.application = application;
        }
    }



    public static void initTree(TreeParent<String> tree)
    {
        mInstance.tree = tree;

        //TODO 这里要改，看看怎么获取第一个页面的node
        mInstance.currentNode = mInstance.tree.root();
    }

    public static void page(Activity activity)
    {
        //TODO 这里要改，看看怎么获取第一个页面的node
//        mInstance.currentNode = mInstance.tree.root();

        PageProxy.getInstance().setNaviListener(mInstance);
        PageProxy.getInstance().setApplication(mInstance.application);

        Field[] fields = activity.getClass().getDeclaredFields();
        Field targetField = null;

        PageAnno pageAnno = activity.getClass().getAnnotation(PageAnno.class);

        for (Field field: fields)
        {
            Class fieldType = field.getType();

            //判断成员变量的类型是否继承或实现自Page
            if (PageListener.class.isAssignableFrom(fieldType))
            {
                targetField = field;
                break;
            }
        }


        if (pageAnno == null)
            throw new RuntimeException("必须要添加PageAnno注解才可调用，注册的对象为:" + activity);

        if (pageAnno.value() == null)
            throw new RuntimeException("PageAnno值不能为空，注册的对象为:" + activity);

        if (targetField == null)
            throw new RuntimeException("必须要添加成员变量com.goteny.melo.navigator.Page才可调用，注册的对象为:" + activity);


        try
        {
            Class clxx = pageAnno.value();
            PageListener pageListener = (PageListener) PageProxy.getInstance().create(clxx);

            LogMelo.d("Page: " + pageListener);

            targetField.setAccessible(true);
            targetField.set(activity, pageListener);
        } catch (Throwable e)
        {
            e.printStackTrace();
        }

    }



    @Override
    public void onNext(Class clxx, Bundle bundle)
    {
        LogMelo.d(clxx + "; " + bundle);

        List<TreeParent.Node<String>> childNodes = mInstance.tree.children(currentNode);

        for (TreeParent.Node<String> node: childNodes)
        {

            //TODO 这里要改，当树里面有两个节点的名字一样时，怎么确定当前是哪个节点
            if (node.data.equals(clxx.getName()))
            {
                currentNode = node;
                ActivityAnno activityAnno = null;

                try
                {
                    Class pageClass = Class.forName(node.data);
                    activityAnno = (ActivityAnno) pageClass.getAnnotation(ActivityAnno.class);
                } catch (Throwable e)
                {
                    e.printStackTrace();
                }

                if (activityAnno == null)
                    throw new RuntimeException("必须要添加ActivityAnno注解才可调用，注册的对象为:" + node.data);

                if (activityAnno.value() == null)
                    throw new RuntimeException("ActivityAnno值不能为空，注册的对象为:" + node.data);

                Class classTarget = activityAnno.value();

                Intent intent = new Intent(application, classTarget);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                application.startActivity(intent);


//                String path = routerMap.get(classTarget);
//
//                if (!TextUtils.isEmpty(path))
//                {
//                    //启动对应的Activity
//                    RouterUtil.getRouter().build(path).with(bundle).navigation();
//                }

            }
        }

        //TODO 是否要关闭上一个activity
        closePage();
    }

    private void closePage()
    {
        //关闭页面直接用反射调用activity的finish
    }
}
