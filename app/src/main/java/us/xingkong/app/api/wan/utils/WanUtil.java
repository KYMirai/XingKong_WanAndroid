package us.xingkong.app.api.wan.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import us.xingkong.app.api.wan.WanAndroidApi;

import static us.xingkong.app.utils.NetUtil.baseUrl;
import static us.xingkong.app.utils.NetUtil.client;

class WanUtil {
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
}