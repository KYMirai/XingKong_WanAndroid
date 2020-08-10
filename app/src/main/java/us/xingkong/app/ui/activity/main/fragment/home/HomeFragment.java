package us.xingkong.app.ui.activity.main.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import butterknife.ButterKnife;
import us.xingkong.app.R;
import us.xingkong.app.ui.base.fragment.BaseFragment;

public class HomeFragment extends BaseFragment<HomePresenter> {
    @Override
    protected View createView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, requireRoot());
    }
}