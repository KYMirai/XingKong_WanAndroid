package us.xingkong.app.api.wan;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import us.xingkong.app.api.wan.bean.ArticleBean;
import us.xingkong.app.api.wan.bean.CoinBean;
import us.xingkong.app.api.wan.bean.LoginBean;
import us.xingkong.app.api.wan.bean.LogoutBean;
import us.xingkong.app.api.wan.bean.RegisterBean;

public interface WanAndroidApi {
    int SUCCESS = 0;
    //int INVALID_TOKEN = -1001; //token过期

    @POST("user/login")
    Observable<LoginBean> login(
            @Query("username") String userName,
            @Query("password") String password);

    @POST("user/register")
    Observable<RegisterBean> register(
            @Query("username") String userName,
            @Query("password") String password,
            @Query("repassword") String rePassword);

    @GET("user/logout/json")
    Observable<LogoutBean> logout();

    @GET("lg/coin/userinfo/json")
    Observable<CoinBean> getCoin();

    @GET("article/list/{page}/json")
    Observable<ArticleBean> getArticle(@Path("page") String page);
}