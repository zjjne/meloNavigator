package com.goteny.melo.navigator;

public class NodePage
{
    public Class<? extends PageListener> pageClass;
    public String bounAction;      //绑定的函数动作

    public NodePage(Class<? extends PageListener> pageClass, String bounAction)
    {
        this.pageClass = pageClass;
        this.bounAction = bounAction;
    }
}
