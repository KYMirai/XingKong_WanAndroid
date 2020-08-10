package us.xingkong.app.api.wan.utils;

import android.annotation.SuppressLint;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import us.xingkong.app.api.wan.bean.LoginBean;

public class UserUtil {
    public interface LoginCallBack {
        void onLogin(LoginBean loginBean);
    }

    /**
     * 登陆，无回调
     **/
    public static void login(String userName, String password) {
        login(userName, password, loginBean -> {
        });
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
                .subscribe(callBack::onLogin);
    }

    public static boolean isLogin() {
        return false;
    }
}