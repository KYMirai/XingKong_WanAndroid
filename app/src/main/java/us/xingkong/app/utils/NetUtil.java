package us.xingkong.app.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import us.xingkong.app.base.BaseObserver;

public class NetUtil {
    //插入cookies
    private static class AddCookiesInterceptor implements Interceptor {
        @SuppressWarnings("NullableProblems")
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String domain = request.url().topPrivateDomain();
            final Request.Builder builder = request.newBuilder();
            StringBuilder cookies = new StringBuilder();
            for (Map.Entry<String, ?> entry : SharedPreferencesUtil.getCookies(domain).getAll().entrySet()) {
                if (entry.getValue() instanceof String) {
                    cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
                }
            }
            if (cookies.length() > 0) builder.addHeader("Cookie", cookies.toString());
            return chain.proceed(builder.build());
        }
    }

    //保存cookies
    private static class SaveCookiesInterceptor implements Interceptor {
        @SuppressWarnings("NullableProblems")
        @SuppressLint("CheckResult")
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String domain = request.url().topPrivateDomain();
            Response originalResponse = chain.proceed(request);
            //这里获取请求返回的cookie
            String[] cookies = originalResponse.headers("Set-Cookie").toArray(new String[]{});
            //noinspection ResultOfMethodCallIgnored
            Observable.fromArray(cookies).subscribe(cookies1 -> {
                final SharedPreferences.Editor editor = SharedPreferencesUtil.getCookies(domain).edit();
                Observable.fromArray(cookies1.split("[,;]")).subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String cookie) {
                        if (cookie.contains("=")) {
                            String[] tmp = cookie.split("=", 2);
                            String key = tmp[0].trim();
                            String value = tmp[1].trim();
                            editor.putString(key, value);
                        }
                    }

                    @Override
                    public void onComplete() {
                        editor.apply();
                    }
                });
            });
            return originalResponse;
        }
    }

    public final static String baseUrl = "https://www.wanandroid.com";
    private final static Interceptor addCookiesInterceptor = new SaveCookiesInterceptor();
    private final static Interceptor saveCookiesInterceptor = new AddCookiesInterceptor();
    public final static OkHttpClient client = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(addCookiesInterceptor)
            .addInterceptor(saveCookiesInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(55, TimeUnit.SECONDS)
            .build();

}
