package us.xingkong.app.ui.base.fragment;

import android.app.Activity;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public abstract class BasePresenter<F extends BaseFragment, M extends BaseModel> {
    private F fragment;
    private M model;

    @SuppressWarnings("unchecked")
    public BasePresenter(F fragment) {
        this.fragment = fragment;
        try {
            this.model = (M) ((Class<M>) ((ParameterizedType) (Objects.requireNonNull(getClass().getGenericSuperclass()))).getActualTypeArguments()[0]).getConstructors()[0].newInstance(this);
        } catch (IllegalAccessException | java.lang.InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected Activity requireActivity(){
        return fragment.requireActivity();
    }

    protected Context requireContext(){
        return fragment.requireContext();
    }

    protected F requireFragment() {
        return fragment;
    }

    protected M requireModel() {
        return model;
    }
}