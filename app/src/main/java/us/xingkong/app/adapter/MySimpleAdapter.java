package us.xingkong.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySimpleAdapter extends BaseAdapter {
    private final Context context;
    private final List<? extends Map<String, ?>> data;
    private final int resource;
    private final String[] from;
    private final int[] to;

    private ViewBinder viewBinder;

    //private final ArrayList<View> views = new ArrayList<>();

    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        this.context = context;
        this.data = data;
        this.resource = resource;
        this.from = from;
        this.to = to;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, null, false);
        }
        loadViewData(position, view);
        return view;
    }

    public void loadViewData(int position, View root) {
        for (int i = 0; i < from.length; i++) {
            View view = root.findViewById(to[i]);
            Object value = data.get(position).get(from[i]);
            if (value != null && (viewBinder == null || !viewBinder.setViewValue(view, from[i], value))) {
                if (view instanceof ImageView) {
                    if (value instanceof String) {
                        if (((String) value).trim().isEmpty())
                            ((ImageView) view).setImageBitmap(null);
                        else
                            Glide.with(context).load((String) value).diskCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) view);
                    } else if (value instanceof Bitmap) {
                        Glide.with(context).load((Bitmap) value).into((ImageView) view);
                    } else if (value instanceof Drawable) {
                        Glide.with(context).load((Drawable) value).into((ImageView) view);
                    } else if (value instanceof File) {
                        Glide.with(context).load((File) value).into((ImageView) view);
                    } else if (value instanceof Uri) {
                        Glide.with(context).load((Uri) value).into((ImageView) view);
                    }
                } else if (view instanceof Button) {
                    ((Button) view).setText(value.toString());
                } else if (view instanceof TextView) {
                    ((TextView) view).setText(value.toString());
                }
            }
        }
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.viewBinder = viewBinder;
    }

    public interface ViewBinder {
        boolean setViewValue(View view, String key, Object value);
    }
}
