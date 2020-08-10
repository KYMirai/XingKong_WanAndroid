package us.xingkong.app.ui.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.xingkong.app.R;
import us.xingkong.app.R2;
import us.xingkong.app.ui.base.BaseActivity;
import us.xingkong.app.ui.activity.main.fragment.home.HomeFragment;
import us.xingkong.app.ui.activity.main.fragment.mine.MineFragment;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity {
    //private static Boolean isExit = false;
    @BindView(R2.id.nav_view)
    BottomNavigationView navView;
    @BindView(R2.id.view_pager)
    ViewPager pager;
    @BindView(R2.id.coordinator)
    CoordinatorLayout coordinator;

    private final HomeFragment homeFragment = new HomeFragment();
    private final MineFragment mineFragment = new MineFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        //fragment的id数组
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(R.id.navigation_home);
        ids.add(R.id.navigation_me);

        //id数组对应的Fragment
        pager.setAdapter(new AppPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, new Fragment[]{homeFragment, mineFragment}));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navView.setSelectedItemId(ids.get(position));
                setTitle(navView.getMenu().getItem(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navView.setOnNavigationItemSelectedListener(item -> {
            pager.setCurrentItem(ids.indexOf(item.getItemId()));
            setTitle(item.getTitle());
            return true;
        });
    }
}