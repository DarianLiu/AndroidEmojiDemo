package com.qingyuan.emojidemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by xiaoyuan on 16/3/11.
 */
public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getmContext() {
        return mContext;
    }
}
