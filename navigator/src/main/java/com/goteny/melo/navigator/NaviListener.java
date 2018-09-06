package com.goteny.melo.navigator;

public interface NaviListener
{
    void onNext(ActionNext actionNext);
    void onFinish(Class<? extends PageListener> finishPage);
}
