package us.xingkong.app.ui.activity.main.fragment.home;

import android.annotation.SuppressLint;
import android.widget.TextView;


import butterknife.BindView;
import us.xingkong.app.R;
import us.xingkong.app.R2;
import us.xingkong.app.ui.base.fragment.BaseFragment;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends BaseFragment<HomePresenter> {
    @BindView(R2.id.home_test)
    TextView tv_test;

    @Override
    protected int getResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        tv_test.setText(requirePresenter().test());
    }
}