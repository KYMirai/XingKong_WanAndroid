package us.xingkong.app.ui.activity.main.fragment.home;

import us.xingkong.app.ui.base.fragment.BasePresenter;

public class HomePresenter extends BasePresenter<HomeFragment, HomeModel> {
    public String test() {
        return requireModel().test();
    }
}