package com.example.abner.listviewwithsoftpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Abner on 15/6/27.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class ListViewForScroll extends ListView {

    public ListViewForScroll(Context context) {
        super(context);
    }

    public ListViewForScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
