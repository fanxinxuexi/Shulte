package project.myshulteproject.com.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by fanxin on 2019/10/28.
 */

public class TableBorderLayout extends TableLayout {
    private Context mContext;
    private BaseAdapter mAdapter;
    private int column = 0; //个数


    public TableBorderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setStretchAllColumns(true);
    }

    public void setAdapter(BaseAdapter baseAdapter, int column) {
        if (baseAdapter == null || baseAdapter.getCount() == 0) {
            return;
        }
        this.mAdapter = baseAdapter;
        this.column = column;//个数

        drawLayout();
    }

    private void drawLayout() {
        removeAllViews();
        int realcount = mAdapter.getCount();//传入的数据个数
        int count = 0;
        if (realcount < column) { // 5 < 4
            count = column;
        } else if (realcount % column != 0) {
            count = realcount + column - (realcount % column);
        } else {
            count = realcount;
        }

        TableRow tableRow = null; //每一行的tableRow
        for (int i = 0; i < count; i++) {
            final int index = i;
            View view = null;
            if (index >= realcount) {
                view = mAdapter.getView((realcount - 1), null, null);
                view.setVisibility(INVISIBLE);
            } else {
                view = mAdapter.getView(index, null, null);
            }

            if (index % column == 0) { //整行
                tableRow = new TableRow(mContext);
            }

            if (tableRow != null) {
                tableRow.addView(view);
            }

            if (index % column == 0) {
                addView(tableRow, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
    }

}
