package com.dsg.hotfixdemo;

import android.app.Application;

/**
 * @author DSG
 * @Project HotFixDemo
 * @date 2020/6/16
 * @describe
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fix.Fix(getClassLoader(), this);
    }
}
