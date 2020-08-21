package us.xingkong.app.ui.activity.main.fragment.home;

import java.util.ArrayList;
import java.util.HashMap;

import us.xingkong.app.api.wan.bean.ArticleBean;
import us.xingkong.app.api.wan.callback.ArticleCallBack;
import us.xingkong.app.api.wan.utils.WanUtil;
import us.xingkong.app.base.fragment.BasePresenter;

public class HomePresenter extends BasePresenter<HomeFragment, HomeModel> {
    final private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private int page = 0;

    interface ListDataCallback {
        void onSuccess();
    }

    void loadMoreListData(boolean clearOldData, ListDataCallback callBack) {
        if (clearOldData) page = 0;
        WanUtil.getArticle(page++, new ArticleCallBack() {
            @Override
            public void onSuccess(ArticleBean.ArticleDataBean dataBean) {
                if (clearOldData) list.clear();
                for (ArticleBean.ArticleDataBean.Data data : dataBean.datas) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("img", data.envelopePic);
                    map.put("title", data.title);
                    map.put("niceDate", data.niceDate);
                    map.put("author", data.author);
                    map.put("shareUser", data.shareUser);
                    map.put("desc", data.desc);
                    map.put("tags", data.tags);
                    list.add(map);
                }
                callBack.onSuccess();
            }

            @Override
            public void onFailed(int code, String errorMsg) {
            }
        });
    }

    ArrayList<HashMap<String, Object>> getListData() {
        return list;
    }
}