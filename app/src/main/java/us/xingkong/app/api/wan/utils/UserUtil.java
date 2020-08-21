package us.xingkong.app.api.wan.utils;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import us.xingkong.app.api.wan.WanAndroidApi;
import us.xingkong.app.api.wan.bean.data.UserDataBean;
import us.xingkong.app.api.wan.callback.CheckCallBack;
import us.xingkong.app.api.wan.callback.CoinCallBack;
import us.xingkong.app.api.wan.callback.LoginCallBack;
import us.xingkong.app.api.wan.callback.LogoutCallBack;
import us.xingkong.app.utils.SharedPreferencesUtil;

public class UserUtil {

    public static boolean isLogin() {
        return isLogin;
    }

    public static UserDataBean getUserData() {
        return userData;
    }

    private static UserDataBean userData;

    private static boolean isLogin = false;


    /**
     * 登陆，无回调
     **/
    public static void login(String userName, String password) {
        login(userName, password, null);
    }

    /**
     * 登陆
     * 测试账号 test147896325 123456
     **/
    @SuppressLint("CheckResult")
    public static void login(String userName, String password, LoginCallBack callBack) {
        //noinspection ResultOfMethodCallIgnored
        WanUtil.getDataService()
                .login(userName, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginBean -> {
                    if (loginBean.errorCode == WanAndroidApi.SUCCESS) {
                        isLogin = true;
                        userData = loginBean.data;
                        SharedPreferencesUtil.getSharedPreferences().edit().putString("UserData", JSONObject.toJSONString(userData)).apply();
                        if (callBack != null) callBack.onSuccess(userData);
                    } else {
                        isLogin = false;
                        if (callBack != null)
                            callBack.onFailed(loginBean.errorCode, loginBean.errorMsg);
                    }
                });
    }

    /**
     * 注册，无回调
     **/
    public static void register(String userName, String password, String repassword) {
        register(userName, password, repassword, null);
    }

    /**
     * 注册
     **/
    @SuppressLint("CheckResult")
    public static void register(String userName, String password, String repassword, LoginCallBack callBack) {
        //noinspection ResultOfMethodCallIgnored
        WanUtil.getDataService()
                .register(userName, password, repassword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginBean -> {
                    if (loginBean.errorCode == WanAndroidApi.SUCCESS) {
                        isLogin = true;
                        userData = loginBean.data;
                        if (callBack != null) callBack.onSuccess(userData);
                    } else {
                        isLogin = false;
                        if (callBack != null)
                            callBack.onFailed(loginBean.errorCode, loginBean.errorMsg);
                    }
                });
    }

    /**
     * 登出，无回调
     **/
    public static void logout() {
        logout(null);
    }

    /**
     * 登出
     **/
    @SuppressLint("CheckResult")
    public static void logout(LogoutCallBack callBack) {
        //noinspection ResultOfMethodCallIgnored
        WanUtil.getDataService()
                .logout()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginBean -> {
                    if (loginBean.errorCode == WanAndroidApi.SUCCESS) {
                        isLogin = false;
                        userData = null;
                        if (callBack != null) callBack.onSuccess();
                    } else {
                        if (callBack != null)
                            callBack.onFailed(loginBean.errorCode, loginBean.errorMsg);
                    }
                });
    }

    /**
     * 检查登录状态，无回调
     * 利用查积分接口实现
     **/
    public static void check() {
        check(null);
    }

    /**
     * 检查登录状态
     * 利用查积分接口实现
     **/
    @SuppressLint("CheckResult")
    public static void check(CheckCallBack callBack) {
        //noinspection ResultOfMethodCallIgnored
        WanUtil.getDataService()
                .getCoin()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinBean -> {
                    if (coinBean.errorCode == WanAndroidApi.SUCCESS) {
                        isLogin = true;
                        if (callBack != null) callBack.onSuccess(coinBean.data);
                    } else {
                        isLogin = false;
                        if (callBack != null)
                            callBack.onFailed(coinBean.errorCode, coinBean.errorMsg);
                    }
                });
    }

    /**
     * 查积分
     * 利用查积分接口实现
     **/
    @SuppressLint("CheckResult")
    public static void getCoin(CoinCallBack callBack) {
        //noinspection ResultOfMethodCallIgnored
        WanUtil.getDataService()
                .getCoin()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinBean -> {
                    if (coinBean.errorCode == WanAndroidApi.SUCCESS) {
                        if (callBack != null) callBack.onSuccess(coinBean.data);
                    } else {
                        if (callBack != null)
                            callBack.onFailed(coinBean.errorCode, coinBean.errorMsg);
                    }
                });
    }

    public static void loadLocalUserData() {
        userData = JSONObject.toJavaObject(JSONObject.parseObject(SharedPreferencesUtil.getSharedPreferences().getString("UserData", "{}")), UserDataBean.class);
    }
}