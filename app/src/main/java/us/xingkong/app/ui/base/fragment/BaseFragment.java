package us.xingkong.app.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import butterknife.ButterKnife;

@SuppressWarnings("rawtypes")
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    private P presenter;
    private View root;

    @SuppressWarnings("unchecked")
    public BaseFragment() {
        super();
        try {
            presenter = (P) ((Class<P>) ((ParameterizedType) (Objects.requireNonNull(getClass().getGenericSuperclass()))).getActualTypeArguments()[0]).newInstance();
            presenter.setFragment(this);
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(getResource(), container, false);
        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    protected P requirePresenter() {
        return presenter;
    }

    protected View requireRoot() {
        return root;
    }

    protected abstract int getResource();

    protected abstract void initView();
}