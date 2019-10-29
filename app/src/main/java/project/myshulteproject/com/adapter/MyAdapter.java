package project.myshulteproject.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.RowSetReader;

import project.myshulteproject.com.R;

/**
 * Created by fanxin on 2019/10/28.
 */

public class MyAdapter extends BaseAdapter {

    private List<Integer> dataList;
    private LayoutInflater mInflater;
    private int type;
    private View.OnClickListener clickListener;
    private Context context;
    private List<Integer> isR = new ArrayList<>();
    private List<Integer> isG = new ArrayList<>();
    private List<Integer> isB = new ArrayList<>();

    public MyAdapter(Context context, List<Integer> dataList, View.OnClickListener clickListener, int type) {
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.clickListener = clickListener;
        this.type = type;
        this.context = context;
    }

    public MyAdapter(Context context, List<Integer> dataList, View.OnClickListener clickListener, int type, List<Integer> isR, List<Integer> isG, List<Integer> isB) {
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.clickListener = clickListener;
        this.type = type;
        this.context = context;
        this.isR = isR;
        this.isG = isG;
        this.isB = isB;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.item_click_interface_list, null);
        }

        TextView textView = view.findViewById(R.id.tvTile);
        if (type == 7) {
            textView.setTextSize(20);
        } else if (type == 9) {
            textView.setTextSize(16);
        }

        if (isR.size() > 0  && isG.size() > 0 && isB.size() > 0){
            textView.setBackgroundColor(Color.rgb(isR.get(i+1), isG.get(i+2), isB.get(i+3)));
        }

        textView.setText("" + dataList.get(i).toString());

        textView.setTag(textView);
        textView.setId(dataList.get(i).intValue());

        textView.setOnClickListener(clickListener);
        return view;
    }
}
