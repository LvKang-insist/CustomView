package com.admin.view_core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.admin.view_core.R;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @author Lv
 * Created at 2019/6/4
 */
public class CustomTitleView extends View {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    /**
     * 坐标 和 画笔
     */
    private Rect mBound;
    private Paint mPaint;

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获取自定义属性的值
     */
    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
         * 获取 自定义属性
         */
        TypedArray type = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int count = type.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = type.getIndex(i);
            if (attr == R.styleable.CustomTitleView_titleText) {
                mTitleText = type.getString(attr);
            } else if (attr == R.styleable.CustomTitleView_titleColor) {
                mTitleTextColor = type.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.CustomTitleView_titleTextSize) {
                //默认设置为 16 sp，TypeValue 可以将sp 转化为 px
                mTitleTextSize = type.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            }

        }
        //回收资源
        type.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        //设置文字的 矩阵
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        //判读是否为 固定值，或者 match_parent
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
            //否则就是 wrap_content
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            width = mBound.width() + (getPaddingLeft() + getPaddingRight());
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            height = mBound.height() +(getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        //绘制一个 矩形
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        //绘制 文字
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2,
                getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}
