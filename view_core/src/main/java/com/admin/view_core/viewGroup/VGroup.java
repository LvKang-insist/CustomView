package com.admin.view_core.viewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Lv
 * Created at 2019/5/25
 */
public class VGroup extends ViewGroup {


    public VGroup(Context context) {
        super(context);
    }

    public VGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的 子View 进行测量，还会触发每个子View 的onMeasure 方法
        //主要 要与measureChild 区分，measureChild 是对单个 view 进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        // 获取 子View 的数量
        int childCount = getChildCount();
        //如果没有 子Vie ，当前的 ViewGroup 没有意义，不用占空间
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            //如果宽度 和 高度都是包裹内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                //将高度设置 为所有 子View 相加的高度，宽度 设置为子View中最大的宽度
                int width = getMaxChildWidth();
                int height = getTitleHeight();
                //设置 测量的 宽度和 高度
                setMeasuredDimension(width, height);
                //如果 高度是包裹内容
            } else if (heightMode == MeasureSpec.AT_MOST) {
                // 宽度设置为ViewGroup 自己测量的宽度，高度为所有 子View高度的总和
                setMeasuredDimension(widthSize, getTitleHeight());
                //如果 宽度是包裹内容
            } else if (widthMode == MeasureSpec.AT_MOST) {
                //宽度 设置为子View 中的最大值，高度为ViewGroup 自己测量值
                setMeasuredDimension(getMaxChildWidth(), heightSize);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();

        //记录当前高度的位置
        int curHeight = 0;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            //摆放子View ，参数分别是 左，上，右，下。
            view.layout(0, curHeight, width , curHeight + height);
            curHeight += height;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE); //描边
        mPaint.setStrokeCap(Paint.Cap.BUTT); //线帽：直线
        mPaint.setStrokeJoin(Paint.Join.BEVEL); // 连接处：直线


     /*   //设置文本大小
        mPaint.setTextSize(50);
        //设置字体类型
        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));// 常规
        //文本对齐方式
        mPaint.setTextAlign(Paint.Align.LEFT);
        //文本
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //获取 基线的坐标,这样文字会位于 自定义View 的中间
        float baseLineY = getHeight() / 2 + ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);*/
        // 开始 的 x ,y
//        canvas.drawText("我是自定义文本", baselineX, baseLineY, mPaint);
    }

    /**
     * @return 返回 ziView 中最大的宽度值
     */
    private int getMaxChildWidth() {
        int child = getChildCount();
        int maxWidth = 0;
        //找到 子View 中最大的宽度
        //getMeasuredWidth : 获取 测量的宽度
        for (int i = 0; i < child; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    private int getTitleHeight() {
        int heightCount = getChildCount();
        int height = 0;
        for (int i = 0; i < heightCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }


}
