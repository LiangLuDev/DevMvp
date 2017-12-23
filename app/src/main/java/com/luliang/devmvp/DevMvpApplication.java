package com.luliang.devmvp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Liang_Lu on 2017/9/7.
 */

public class DevMvpApplication extends Application {
    private static DevMvpApplication app;

    public static Context getAppContext() {
        return app;
    }

    public static Resources getAppResources() {
        return app.getResources();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }
}
