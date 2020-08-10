package us.xingkong.app.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    private P presenter;
    private View root;

    @SuppressWarnings("unchecked")
    public BaseFragment() {
        super();
        try {
            presenter = (P) ((Class<P>) ((ParameterizedType) (Objects.requireNonNull(getClass().getGenericSuperclass()))).getActualTypeArguments()[0]).getConstructors()[0].newInstance(this);
        } catch (IllegalAccessException | java.lang.InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.root = createView(inflater, container, savedInstanceState);
        initView();
        return root;
    }

    protected abstract View createView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected P requirePresenter() {
        return presenter;
    }

    protected View requireRoot() {
        return root;
    }

    protected abstract void initView();
}
