package us.xingkong.app.ui.activity.main.fragment.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.xingkong.app.R;
import us.xingkong.app.R2;
import us.xingkong.app.api.wan.utils.UserUtil;
import us.xingkong.app.ui.activity.login.LoginActivity;
import us.xingkong.app.ui.base.fragment.BaseFragment;
import us.xingkong.app.utils.Utils;

@SuppressLint("NonConstantResourceId")
public class MineFragment extends BaseFragment<MinePresenter> {
    public static final int REQUEST_LOGIN = 200;
    public static final int RESULT_LOGIN_SUCCESS = 201;
    public static final int RESULT_LOGIN_ERROR = 202;
    public static final int RESULT_LOGIN_CANCEL = 203;

    @BindView(R2.id.user_name)
    TextView tv_userName;

    @Override
    protected int getResource() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R2.id.mine_login)
    public void login() {
        if (!UserUtil.isLogin()) {
            startActivityForResult(new Intent(requireActivity(), LoginActivity.class), REQUEST_LOGIN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            switch (resultCode) {
                case RESULT_LOGIN_SUCCESS:
                    tv_userName.setText(UserUtil.getUserData().nickname);
                    break;
                case RESULT_LOGIN_ERROR:
                    Utils.showSnackBar(tv_userName, "登录失败");
                    break;
                case RESULT_LOGIN_CANCEL:
                default:
            }
        }
    }
}