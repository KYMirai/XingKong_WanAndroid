package us.xingkong.app.ui.activity.main.fragment.home;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import us.xingkong.app.R;
import us.xingkong.app.R2;
import us.xingkong.app.api.wan.bean.ArticleBean;
import us.xingkong.app.adapter.MySimpleAdapter;
import us.xingkong.app.base.fragment.BaseFragment;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends BaseFragment<HomePresenter> implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R2.id.home_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R2.id.home_list)
    ListView listView;

    private MySimpleAdapter adapter;

    @Override
    protected int getResource() {
        return R.layout.fragment_home;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i + i1 >= i2 && !swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                    loadMoreListData(false, () -> swipeRefreshLayout.setRefreshing(false));
                }
            }
        });
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initData() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginEnd(8);
        adapter = new MySimpleAdapter(requireContext(), requirePresenter().getListData(), R.layout.home_item,
                new String[]{"img", "title", "author", "shareUser", "niceDate", "desc", "tags"},
                new int[]{R.id.item_img, R.id.item_title, R.id.item_author, R.id.item_author, R.id.item_nice_date, R.id.item_desc, R.id.item_tags});
        adapter.setViewBinder((view, key, value) -> {
            if (view.getId() == R.id.item_author) {
                if (value instanceof String && !((String) value).isEmpty())
                    ((TextView) view).setText(Html.fromHtml((String) value));
                return true;
            } else if (view.getId() == R.id.item_tags) {
                ViewGroup viewGroup = (ViewGroup) view;
                viewGroup.removeAllViews();
                for (ArticleBean.ArticleDataBean.Data.Tag tag : (ArticleBean.ArticleDataBean.Data.Tag[]) value) {
                    ViewGroup tagView = (ViewGroup) getLayoutInflater().inflate(R.layout.home_item_tag, null, false);
                    ((TextView) tagView.findViewById(R.id.tag)).setText(tag.name);
                    viewGroup.addView(tagView, layoutParams);
                }
                return true;
            } else if (view instanceof TextView && view.getId() == R.id.item_desc) {
                if (((String) value).isEmpty()) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                    ((TextView) view).setText(Html.fromHtml((String) value));
                }
                return true;
            }
            return false;
        });
        listView.setAdapter(adapter);
        loadMoreListData(true, null);
    }

    public static void main(String... args) {
        System.out.println("author".matches("author|shareUser"));
    }

    private void loadMoreListData(boolean clearOldData, @Nullable Runnable runnable) {
        requirePresenter().loadMoreListData(clearOldData, () -> {
            adapter.notifyDataSetChanged();
            if (runnable != null) runnable.run();
        });
    }

    @Override
    public void onRefresh() {
        loadMoreListData(true, () -> swipeRefreshLayout.setRefreshing(false));
    }
}