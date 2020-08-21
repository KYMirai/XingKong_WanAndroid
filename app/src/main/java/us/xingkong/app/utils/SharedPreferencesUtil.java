package us.xingkong.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.HashMap;

import us.xingkong.app.base.BaseApplication;

public class SharedPreferencesUtil {
    //私有静态类，懒加载，线程安全
    private static class Cookies {
        private static final HashMap<String, SharedPreferences> cookies = new HashMap<>();
    }

    private static class MySharedPreferences {
        private static final SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        return MySharedPreferences.sharedPreferences;
    }

    public static SharedPreferences getCookies(@NonNull String domain) {
        if (Cookies.cookies.containsKey(domain)) {
            return Cookies.cookies.get(domain);
        } else {
            SharedPreferences tmpSP = BaseApplication.getApplication().getSharedPreferences("cookies_" + domain, Context.MODE_PRIVATE);
            Cookies.cookies.put(domain, tmpSP);
            return tmpSP;
        }
    }
}