package com.guc.bureau2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.guc.bureau2.R;

public class SlideTitleLayout extends LinearLayout implements View.OnTouchListener {
    private int touchSlop;
    private float yDown;
    private RelativeLayout linearLayout;
    private LayoutParams layoutParams;
    private int hideHeaderHeight;
    private boolean loadOnce = false;
    private ListView listView;
    float old_yMove = 0;

    public SlideTitleLayout(Context context) {
        super(context);
    }

    public SlideTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float yMove = event.getRawY();
                if (old_yMove == 0) {
                    old_yMove = yMove;
                }
                int dx = (int) (yMove - old_yMove);
                old_yMove = event.getRawY();
                float distance = yMove - yDown;
                if (Math.abs(distance) < touchSlop) {
                    return false;
                }
                int now_topMargin = layoutParams.topMargin;
                if (now_topMargin < -hideHeaderHeight && dx < 0) {
                    return false;
                }
                if (now_topMargin > 0 && dx > 0||dx>0&&listView.getFirstVisiblePosition()!=0) {
                    return false;
                }
                if (dx > 0 && now_topMargin + dx > 0) {
                    layoutParams.topMargin = 0;
                    linearLayout.setLayoutParams(layoutParams);
                    return false;
                }
                if (dx < 0 && now_topMargin + dx < -hideHeaderHeight) {
                    layoutParams.topMargin = -hideHeaderHeight;
                    linearLayout.setLayoutParams(layoutParams);
                    return false;
                }
                layoutParams.topMargin = now_topMargin + dx;
                linearLayout.setLayoutParams(layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                old_yMove = 0;
                break;
        }
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce) {
            linearLayout = (RelativeLayout) getChildAt(0);
            layoutParams = (LayoutParams) linearLayout.getLayoutParams();
            hideHeaderHeight = layoutParams.height;
            listView = (ListView) findViewById(R.id.listView);
            listView.setOnTouchListener(this);
            loadOnce = true;
        }
    }
}
