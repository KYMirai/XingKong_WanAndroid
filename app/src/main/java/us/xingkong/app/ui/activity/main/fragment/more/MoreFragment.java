package us.xingkong.app.ui.activity.main.fragment.more;

import android.annotation.SuppressLint;
import us.xingkong.app.R;
import us.xingkong.app.base.fragment.BaseFragment;

@SuppressLint("NonConstantResourceId")
public class MoreFragment extends BaseFragment<MorePresenter> {
    @Override
    protected int getResource() {
        return R.layout.fragment_more;
    }
}