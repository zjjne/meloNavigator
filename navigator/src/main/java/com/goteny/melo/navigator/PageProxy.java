package com.goteny.melo.navigator;


import android.app.Application;

import com.goteny.melo.utils.log.LogMelo;
import com.goteny.melo.utils.cglibAndroid.proxy.Enhancer;
import com.goteny.melo.utils.cglibAndroid.proxy.MethodInterceptor;
import com.goteny.melo.utils.cglibAndroid.proxy.MethodProxy;

/**
 * 代理类
 */

@SuppressWarnings("unchecked")
public class PageProxy implements MethodInterceptor
{

    private static PageProxy mInstance;


    NaviListener naviListener = null;
    Application application = null;



    public void setApplication(Application application)
    {
        this.application = application;
    }

    public void setNaviListener(NaviListener listener)
    {
        naviListener = listener;
    }




    @Override
    public Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Exception
    {
        return PageHandler.invokeMethod(object, methodProxy, args, naviListener);
    }



    public static PageProxy getInstance()
    {
        if (mInstance == null)
            mInstance = new PageProxy();

        return mInstance;
    }

    private PageProxy()
    {

    }



    /**
     * 创建API接口代理对象
     */
    public <T> T create(T obj)
    {
        LogMelo.d("create(T obj)");

        Enhancer enhancer = new Enhancer(application);
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);

        //创建代理对象
        return (T) enhancer.create();
    }

    /**
     * 创建API接口代理对象
     */
    public <T> T create(Class<T> clxx)
    {
        LogMelo.d("create(Class<T> clxx)");

        Enhancer enhancer = new Enhancer(application);
        enhancer.setSuperclass(clxx);
        enhancer.setCallback(this);

        //创建代理对象
        return (T) enhancer.create();
    }
}
