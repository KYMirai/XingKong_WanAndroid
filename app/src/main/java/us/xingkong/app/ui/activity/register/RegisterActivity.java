package us.xingkong.app.ui.activity.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.iangclifton.android.floatlabel.FloatLabel;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import us.xingkong.app.R;
import us.xingkong.app.R2;
import us.xingkong.app.api.wan.bean.data.UserDataBean;
import us.xingkong.app.api.wan.callback.LoginCallBack;
import us.xingkong.app.api.wan.utils.UserUtil;
import us.xingkong.app.msg.RegisterMsg;
import us.xingkong.app.base.BaseActivity;
import us.xingkong.app.utils.Utils;

@SuppressLint("NonConstantResourceId")
public class RegisterActivity extends BaseActivity {
    @BindView(R2.id.register_account)
    FloatLabel et_account;
    @BindView(R2.id.register_password)
    FloatLabel et_password;
    @BindView(R2.id.register_repassword)
    FloatLabel et_repassword;
    private boolean registering = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @OnClick(R2.id.tv_login_now)
    public void login() {
        finish();
    }

    @OnClick(R2.id.btn_register)
    public void register(View btn_register) {
        String account = et_account.getEditText().getText().toString();
        String password = et_password.getEditText().getText().toString();
        String repassword = et_repassword.getEditText().getText().toString();
        InputMethodManager manager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            manager.hideSoftInputFromWindow(btn_register.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (!registering) {
            if (TextUtils.isEmpty(account)) {
                Utils.showSnackBar(btn_register, "请输入账号");
            } else if (TextUtils.isEmpty(password)) {
                Utils.showSnackBar(btn_register, "请输入密码");
            } else if (TextUtils.isEmpty(repassword)) {
                Utils.showSnackBar(btn_register, "请再次输入密码");
            } else if (!password.equals(repassword)) {
                Utils.showSnackBar(btn_register, "两次输入的密码不一致");
            } else {
                registering = true;
                register(account, password, repassword, () -> registering = false);
            }
        }
    }

    public void register(String account, String password, String repassword, Runnable runnable) {
        UserUtil.register(account, password, repassword, new LoginCallBack() {
            @Override
            public void onSuccess(UserDataBean userData) {
                runnable.run();
                EventBus.getDefault().post(new RegisterMsg(account, password));
                finish();
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                Utils.showSnackBar(et_account, errorMsg);
                runnable.run();
            }
        });
    }
}
