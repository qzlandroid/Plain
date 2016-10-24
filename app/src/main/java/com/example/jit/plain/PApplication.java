package com.example.jit.plain;


import android.app.Application;

import com.example.jit.core.AppAction;
import com.example.jit.core.AppActionImpl;

import org.xutils.x;

/**
 * Application类，应用级别的操作都放这里
 *
 */
public class PApplication extends Application {

    public AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//Xutils初始化
        appAction = new AppActionImpl(this);
    }

    public AppAction getAppAction() {
        return appAction;
    }
}