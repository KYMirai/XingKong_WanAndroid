package us.xingkong.app.ui.activity.login;

import android.os.Bundle;

import butterknife.ButterKnife;
import us.xingkong.app.R;
import us.xingkong.app.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
