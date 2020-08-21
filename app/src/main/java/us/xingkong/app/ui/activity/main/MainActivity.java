package us.xingkong.app.ui.activity.main;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.app.R;
import us.xingkong.app.R2;
import us.xingkong.app.api.wan.bean.CoinBean;
import us.xingkong.app.api.wan.callback.CheckCallBack;
import us.xingkong.app.api.wan.utils.UserUtil;
import us.xingkong.app.adapter.MyFragmentPagerAdapter;
import us.xingkong.app.ui.activity.main.fragment.more.MoreFragment;
import us.xingkong.app.base.BaseActivity;
import us.xingkong.app.ui.activity.main.fragment.home.HomeFragment;
import us.xingkong.app.ui.activity.main.fragment.mine.MineFragment;
import us.xingkong.app.base.fragment.BaseFragment;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity {
    private static Boolean isExit = false;
    @BindView(R2.id.nav_view)
    BottomNavigationView navView;
    @BindView(R2.id.view_pager)
    ViewPager pager;
    @BindView(R2.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R2.id.main_root)
    RelativeLayout root;
    @BindView(R2.id.main_title)
    TextView title;
    @BindView(R2.id.tab)
    LinearLayout tab;

    private enum TabPosition {in, out}

    private TabPosition tabPosition = TabPosition.out;

    //控制标题栏栏显示位置 在'我'的界面不显示标题栏
    private synchronized void moveTab(TabPosition changePosition) {
        if (tabPosition != changePosition) {
            System.out.println(changePosition);
            tabPosition = changePosition;
            if (changePosition == TabPosition.in) {
                tab.setVisibility(View.GONE);
            } else {
                tab.setVisibility(View.VISIBLE);
            }
        }
    }

    //Fragment
    private final HomeFragment homeFragment = new HomeFragment();
    private final MoreFragment moreFragment = new MoreFragment();
    private final MineFragment mineFragment = new MineFragment();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        //fragment的id数组
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(R.id.navigation_home);
        ids.add(R.id.navigation_more);
        ids.add(R.id.navigation_mine);
        BaseFragment<?>[] fragments = new BaseFragment[]{homeFragment, moreFragment, mineFragment};

        //id数组对应的Fragment
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments);
        pager.setOnScrollChangeListener((view, i, i1, i2, i3) -> moveTab((i > dm.widthPixels * (adapter.getCount() - 2)) ? TabPosition.in : TabPosition.out));
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i("TAG", "onPageScrolled: " + positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                //Log.i("TAG", "onPageSelected: 选中page：" + position);
                navView.setSelectedItemId(ids.get(position));
                setTitle(navView.getMenu().getItem(position).getTitle());
                fragments[position].onSwitch();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navView.setOnNavigationItemSelectedListener(item -> {
            //Log.i("TAG", "initView: 选中nav：" + ids.indexOf(item.getItemId()));
            int position = ids.indexOf(item.getItemId());
            pager.setCurrentItem(position);
            setTitle(item.getTitle());
            if (position < ids.size() - 1) setTitle(item.getTitle().toString());
            return true;
        });
    }

    @Override
    protected void initData() {
        UserUtil.check(new CheckCallBack() {
            public void onSuccess(CoinBean.CoinDataBean dataBean) {
                //Log.i("TAG", "onSuccess: 已经登录");
                UserUtil.loadLocalUserData();
                mineFragment.loadLocalUserData();
            }
        });
        setTitle(navView.getMenu().getItem(0).getTitle().toString());
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    @SuppressLint("ShowToast")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                finish();
            } else {
                isExit = true;
                Snackbar.make(pager, "再按一次退出", Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        isExit = false;
                    }
                }).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}