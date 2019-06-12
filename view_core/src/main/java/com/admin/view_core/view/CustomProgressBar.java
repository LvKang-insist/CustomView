package com.admin.view_core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.admin.view_core.R;

/**
 * @author Lv
 * Created at 2019/6/4
 */
public class CustomProgressBar extends View {
    //第一圈的颜色
    private int mFirstColor;
    //第二圈的颜色
    private int mSecondColor;
    //圆的宽度
    private int mCircleWidth;
    //画笔
    private Paint mPaint;
    //当前进度
    private int mProgress;
    //速度
    private int mSpeed;
    // 是否应该 开始下一个
    private boolean isNext = false;
    // 状态
    private volatile boolean isState = true;
    private RectF oval;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ObjectAnimatorBinding")
    public void start() {
        isState = true;
        new Thread() {
            @Override
            public void run() {
                while (isState) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        isNext = !isNext;
                    }
                    postInvalidate();
                    try {
                        sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void stop() {
        isState = false;
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomProgressBar, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int arr = a.getIndex(i);
            if (arr == R.styleable.CustomProgressBar_firstColor) {
                mFirstColor = a.getColor(arr, Color.BLACK);
            } else if (arr == R.styleable.CustomProgressBar_secondColor) {
                mSecondColor = a.getColor(arr, Color.BLACK);
            } else if (arr == R.styleable.CustomProgressBar_circleWidth) {
                mCircleWidth = a.getDimensionPixelSize(arr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                        20, getResources().getDisplayMetrics()));
            } else if (arr == R.styleable.CustomProgressBar_speed) {
                mSpeed = a.getIndex(arr);
            }
        }
        a.recycle();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2; //圆心 的坐标
        int radius = center - mCircleWidth / 2; //半径

        mPaint.setStrokeWidth(mCircleWidth);//圆环 宽度
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//描边

        if (oval == null) {
            oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        }
        if (isNext) {
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
    }
}
