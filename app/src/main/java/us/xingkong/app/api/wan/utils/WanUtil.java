package us.xingkong.app.api.wan.utils;

import android.annotation.SuppressLint;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import us.xingkong.app.api.wan.WanAndroidApi;
import us.xingkong.app.api.wan.bean.ArticleBean;
import us.xingkong.app.api.wan.callback.ArticleCallBack;
import us.xingkong.app.api.wan.callback.CoinCallBack;

import static us.xingkong.app.utils.NetUtil.baseUrl;
import static us.xingkong.app.utils.NetUtil.client;

public class WanUtil {
    private static WanAndroidApi wanAndroidApi = null;

    /**
     * 获取wanAndroidApi
     */
    static synchronized WanAndroidApi getDataService() {
        if (wanAndroidApi == null) {
            wanAndroidApi = new Retrofit.Builder().client(client).baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(WanAndroidApi.class);
        }
        return wanAndroidApi;
    }


    /**
     * 获取首页文章
     **/
    @SuppressLint("CheckResult")
    public static void getArticle(int page, ArticleCallBack callBack) {
        //noinspection ResultOfMethodCallIgnored
        getDataService()
                .getArticle(String.valueOf(page))
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
}