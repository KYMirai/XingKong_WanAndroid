package us.xingkong.app.ui.activity.main.fragment.mine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import us.xingkong.app.R;
import us.xingkong.app.R2;
import us.xingkong.app.api.wan.bean.data.UserDataBean;
import us.xingkong.app.api.wan.callback.LogoutCallBack;
import us.xingkong.app.api.wan.utils.UserUtil;
import us.xingkong.app.ui.activity.login.LoginActivity;
import us.xingkong.app.base.fragment.BaseFragment;

@SuppressLint("NonConstantResourceId")
public class MineFragment extends BaseFragment<MinePresenter> {
    @BindView(R2.id.user_name)
    TextView tv_userName;
    @BindView(R2.id.mine_account)
    TextView tv_account;

    @Override
    protected int getResource() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    @OnClick(R2.id.mine_login)
    public void onLogin() {
        if (!UserUtil.isLogin()) {
            startActivity(new Intent(requireActivity(), LoginActivity.class));
        }
    }

    @OnLongClick(R2.id.mine_login)
    public void OnLongClick() {
        if (UserUtil.isLogin()) {
            new AlertDialog.Builder(requireContext()).setItems(new String[]{"更换昵称", "退出登录"}, (dialogInterface, i) -> {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        logout();
                        break;
                    default:
                }
            }).create().show();
        }
    }

    @Override
    public void onSwitch() {
        if (!UserUtil.isLogin()) {
            loadLogoutData();
        } else {
            loadLocalUserData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 退出登录
     */
    private void logout() {
        UserUtil.logout(new LogoutCallBack() {
            @Override
            public void onSuccess() {
                loadLogoutData();
            }
        });
    }

    /**
     * 加载储存在本地的用户信息
     */
    public void loadLocalUserData() {
        if (tv_account != null) tv_account.setText(UserUtil.getUserData().username);
        if (tv_userName != null) tv_userName.setText(UserUtil.getUserData().nickname);
    }

    /**
     * 退出登录后清除消息
     */
    private void loadLogoutData() {
        tv_account.setText(R.string.item);
        tv_userName.setText(R.string.login);
    }

    /**
     * 登录成功后回调
     **/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLoginMsg(UserDataBean userData) {
        tv_account.setText(userData.username);
        tv_userName.setText(userData.nickname);
    }
}