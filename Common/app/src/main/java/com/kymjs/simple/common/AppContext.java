package com.kymjs.simple.common;

import android.app.Application;

/**
 * Created by ZhangTao on 16/10/20.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        CustomActivityOnCrash.install(ContextTrojan.getApplicationContext());
    }
}
