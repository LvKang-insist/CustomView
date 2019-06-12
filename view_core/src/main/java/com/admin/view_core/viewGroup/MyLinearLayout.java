package com.admin.view_core.viewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author Lv
 * Created at 2019/6/1
 */
public class MyLinearLayout extends LinearLayout {
    private Paint paint;
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        paint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.parseColor("#ED6F99"));
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(100,100,30,paint);
        canvas.drawCircle(300,300,50,paint);
        canvas.drawCircle(300,500,80,paint);
        canvas.drawCircle(700,300,20,paint);
    }
}
