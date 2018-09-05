package com.goteny.melo.navigator;


import android.os.Bundle;
import android.util.Log;

import com.goteny.melo.navigator.annotation.Next;
import com.goteny.melo.utils.log.LogMelo;
import com.mdit.library.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * 请求BaseRequest对象创建助手类
 */

@SuppressWarnings("unchecked")
public class PageHandler
{




    public static Object invokeMethod(Object proxy, MethodProxy method, Object[] args, NaviListener listener)
    {
//        LogMelo.d("method toGenericString:" + method.toGenericString());
        LogMelo.d("method:" + method);
//        LogMelo.d("proxy:" + proxy);
        LogMelo.d("method name:" + method.getMethodName());
        LogMelo.d("args:" + args);


//        BindNext bindNext = method.getOriginalMethod().getAnnotation(BindNext.class);
//        BindNext bindNext2 = method.getProxyMethod().getAnnotation(BindNext.class);
//
//        LogMelo.d(method.getOriginalMethod().getDeclaringClass().getSuperclass().getName());
//        LogMelo.d(method.getOriginalMethod());
//        LogMelo.d(method.getProxyMethod());


//        Annotation[] annotations = method.getOriginalMethod().getDeclaredAnnotations();
//        LogMelo.d("annotations.length:" + annotations.length);


        Class superClass = method.getOriginalMethod().getDeclaringClass().getSuperclass();
        try
        {
            //TODO 以后改造，此处可改为判断object是否boolean值true，是的话，就拦截这个页面跳转
            Object object = method.invokeSuper(proxy, args);

            LogMelo.d("" + object);

            Method superMethod = superClass.getMethod(method.getMethodName(), method.getArgsType());

            Next next = superMethod.getAnnotation(Next.class);

            Log.d(PageHandler.class.getSimpleName(), "" + next);

            if (next != null)
            {

                LogMelo.d("" + next.value());

                Bundle bundle = null;

                if (args != null && args.length > 0)
                {
                    bundle = (Bundle) args[0];
                }

                if (next.value() != null)
                {
                    Class clxx = next.value();
                    listener.onNext(clxx, bundle);
                }

            }

            return object;

        } catch (Throwable e)
        {
            e.printStackTrace();
        }

        return null;
    }
    
}
