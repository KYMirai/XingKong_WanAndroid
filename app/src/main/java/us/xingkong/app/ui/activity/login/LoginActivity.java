package us.xingkong.app.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.iangclifton.android.floatlabel.FloatLabel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import us.xingkong.app.R;
import us.xingkong.app.R2;
import us.xingkong.app.api.wan.bean.data.UserDataBean;
import us.xingkong.app.api.wan.callback.LoginCallBack;
import us.xingkong.app.api.wan.utils.UserUtil;
import us.xingkong.app.msg.RegisterMsg;
import us.xingkong.app.ui.activity.register.RegisterActivity;
import us.xingkong.app.base.BaseActivity;
import us.xingkong.app.utils.Utils;

@SuppressLint("NonConstantResourceId")
public class LoginActivity extends BaseActivity {
    @BindView(R2.id.login_account)
    FloatLabel et_account;
    @BindView(R2.id.login_password)
    FloatLabel et_password;
    private boolean logging = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R2.id.tv_reg_now)
    public void onRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R2.id.btn_login)
    public void onLogin(View btn_login) {
        InputMethodManager manager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            manager.hideSoftInputFromWindow(btn_login.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (!logging) {
            if (TextUtils.isEmpty(et_account.getEditText().getText())) {
                Utils.showSnackBar(btn_login, "请输入账号");
            } else if (TextUtils.isEmpty(et_password.getEditText().getText())) {
                Utils.showSnackBar(btn_login, "请输入密码");
            } else {
                logging = true;
                login(() -> logging = false);
            }
        }
    }

    /**
     * 登录
     */
    public void login(Runnable runnable) {
        UserUtil.login(et_account.getEditText().getText().toString(), et_password.getEditText().getText().toString(), new LoginCallBack() {
            @Override
            public void onSuccess(UserDataBean userData) {
                runnable.run();
                EventBus.getDefault().post(userData);
                finish();
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                runnable.run();
                Utils.showSnackBar(et_account, errorMsg);
            }
        });
    }

    /**
     * 注册成功后回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetRegMsg(RegisterMsg msg) {
        et_account.setText(msg.account);
        et_password.setText(msg.password);
    }
}