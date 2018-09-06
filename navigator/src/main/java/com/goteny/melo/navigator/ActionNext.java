package com.goteny.melo.navigator;

import android.os.Bundle;

public class ActionNext
{
    public String action;
    public Class<? extends PageListener> currentPage;
    public Class<? extends PageListener> nextPage;
    public Bundle bundle;
}
