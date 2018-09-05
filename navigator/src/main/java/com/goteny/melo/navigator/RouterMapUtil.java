//package com.goteny.melo.navigator;
//
//import android.app.Activity;
//
//import com.alibaba.android.arouter.facade.model.RouteMeta;
//import com.alibaba.android.arouter.facade.template.IRouteGroup;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//
//public class RouterMapUtil
//{
//
//    public static Map<Class<? extends Activity>, String> fetchRouterMap()
//    {
//        Map<Class<? extends Activity>, String> routerMap = new HashMap<>();
//
//        try
//        {
//            Class warehouseClass = Class.forName("com.alibaba.android.arouter.core.Warehouse");
//            Field groupsIndexField = warehouseClass.getDeclaredField("groupsIndex");
//            groupsIndexField.setAccessible(true);
//
//            Constructor warehouseCons = warehouseClass.getDeclaredConstructor();
//            warehouseCons.setAccessible(true);
//            Object warehouseObj = warehouseCons.newInstance();
//
//            Map<String, Class<? extends IRouteGroup>> groupsIndex = (Map<String, Class<? extends IRouteGroup>>) groupsIndexField.get(warehouseObj);
//
//            for (Map.Entry<String, Class<? extends IRouteGroup>> entry : groupsIndex.entrySet())
//            {
//                Class<? extends IRouteGroup> groupMeta = entry.getValue();
//                IRouteGroup iGroupInstance = groupMeta.getConstructor().newInstance();
//                Map<String, RouteMeta> routes = new HashMap<>();
//                iGroupInstance.loadInto(routes);
//
//
//                for (Map.Entry<String, RouteMeta> subEntry : routes.entrySet())
//                {
//                    RouteMeta routeMeta = subEntry.getValue();
//                    String path = routeMeta.getPath();
//                    Class destination = routeMeta.getDestination();
//
//                    if (Activity.class.isAssignableFrom(destination))
//                    {
//                        routerMap.put(destination, path);
//                    }
//
//                }
//
//            }
//
//        } catch (Throwable e)
//        {
//            e.printStackTrace();
//        }
//
//        return routerMap;
//    }
//}
