package com.guc.bureau2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;


public class LineGridView extends GridView {
    public LineGridView(Context context) {
        super(context);
    }

    public LineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Paint localPaint = new Paint();
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setStrokeWidth(1);
        localPaint.setColor(Color.parseColor("#AAAAAA"));
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View cellView = getChildAt(i);
            int right = cellView.getRight();
            int top = cellView.getTop();
            int bottom = cellView.getBottom();
            int left = cellView.getLeft();
            canvas.drawLine(right, top, right, bottom, localPaint);
            canvas.drawLine(left, bottom , right, bottom, localPaint);
        }
    }
}