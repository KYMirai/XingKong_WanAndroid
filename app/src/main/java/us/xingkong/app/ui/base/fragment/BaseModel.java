package us.xingkong.app.ui.base.fragment;

import androidx.lifecycle.ViewModel;

public class BaseModel<P extends BasePresenter> extends ViewModel {
    private P presenter;

    public BaseModel(P presenter) {
        this.presenter = presenter;
    }

    protected P requirePresenter() {
        return presenter;
    }
}