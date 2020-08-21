package us.xingkong.app.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Application application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getApplication() {
        return application;
    }
}