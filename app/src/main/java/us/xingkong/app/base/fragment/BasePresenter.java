package us.xingkong.app.base.fragment;

import android.app.Activity;
import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public abstract class BasePresenter<F extends BaseFragment, M extends BaseModel> {
    private F fragment;
    private M model;

    void setFragment(F fragment) {
        this.fragment = fragment;
    }

    @SuppressWarnings("unchecked")
    public BasePresenter() {
        try {
            model = ((Class<M>) ((ParameterizedType) (Objects.requireNonNull(getClass().getGenericSuperclass()))).getActualTypeArguments()[1]).newInstance();
            model.setPresenter(this);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    protected Activity requireActivity() {
        return fragment.requireActivity();
    }

    protected Context requireContext() {
        return fragment.requireContext();
    }

    protected F requireFragment() {
        return fragment;
    }

    protected M requireModel() {
        return model;
    }
}