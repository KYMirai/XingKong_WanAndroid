package us.xingkong.app.ui.base.fragment;

import androidx.lifecycle.ViewModel;

@SuppressWarnings("rawtypes")
public class BaseModel<P extends BasePresenter> extends ViewModel {
    private P presenter;

    void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    protected P requirePresenter() {
        return presenter;
    }
}