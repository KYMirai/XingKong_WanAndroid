package us.xingkong.app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final Fragment[] fragments;

    @Override
    public int getCount() {
        return fragments.length;
    }

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, Fragment[] fragments) {
        super(fm, behavior);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
}