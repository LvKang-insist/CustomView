package com.admin.view_core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import com.admin.view_core.R;


/**
 * @author Lv
 * Created at 2019/6/5
 */
public class CustomVolumeControlBar extends View {

    private int mFirstColor; //第一圈的颜色
    private int mSecondColor; //第二圈的颜色
    private int mCircleWidth; //圆的宽度
    private Paint mPaint;   //画笔
    private int mCurrentCount;  //当前进度
    private Bitmap mImage;  //中间的图片
    private int mSplitSize; //间隙
    private int mCount; //个数
    private Rect mRect; //记录圆 坐标

    Rect rect; //记录 View 的坐标
    boolean top = false;
    boolean bottom = false;
    int[] pos = new int[2];

    public CustomVolumeControlBar(Context context) {
        this(context, null);
    }

    public CustomVolumeControlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVolumeControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomVolumeControlBar, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomVolumeControlBar_firstColor) {
                mFirstColor = a.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.CustomVolumeControlBar_secondColor) {
                mSecondColor = a.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.CustomVolumeControlBar_bg) {
                mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
            } else if (attr == R.styleable.CustomVolumeControlBar_circleWidth) {
                mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.CustomVolumeControlBar_dotCount) {
                mCount = a.getInt(attr, 20);
            } else if (attr == R.styleable.CustomVolumeControlBar_splitSize) {
                mSplitSize = a.getInt(attr, 20);
            }
        }
        a.recycle();
        mPaint = new Paint();
        mRect = new Rect();
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        getLocationInWindow(pos); //获取位于屏幕的位置 保存到 数组中
        rect.left = pos[0];
        rect.top = pos[1];
        rect.right = pos[0] + getWidth();
        rect.bottom = pos[1] + getHeight();

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆头
        mPaint.setStyle(Paint.Style.STROKE);

        int centre = getWidth() / 2; //圆心 坐标
        int radius = centre - mCircleWidth / 2;// 外圆 的半径

        drawOval(canvas, centre, radius);

        int relRadius = radius - mCircleWidth / 2;//内圆 的半径

        //内切正方形 的位置
        mRect.left = (int) ((relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth);
        mRect.top = (int) ((relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth);
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

        if (mImage.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            mRect.right = (mRect.left + mImage.getWidth());
            mRect.bottom = (mRect.top + mImage.getHeight());
        }
        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                int center = (rect.bottom - rect.top) / 2 + rect.top;
                if (event.getRawY() > rect.top && event.getRawY() < center) {
                    if (event.getRawX() > rect.left && event.getRawX() < rect.right) {
                        top = true;
                    }
                }
                if (event.getRawY() > center && event.getRawY() < rect.bottom) {
                    if (event.getRawX() > rect.left && event.getRawX() < rect.right) {
                        bottom = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (top) {
                    if (mCurrentCount < mCount && mCurrentCount >= 0) {
                        mCurrentCount++;
                        invalidate();
                    }
                }
                if (bottom) {
                    if (mCurrentCount <= mCount && mCurrentCount > 0) {
                        mCurrentCount--;
                        invalidate();
                    }
                }
                initSite();
                break;
        }
        return true;
    }

    private void initSite() {
        top = false;
        bottom = false;
    }

    private void drawOval(Canvas canvas, int centre, int radius) {
        /*
         * 根据 需要画的个数 计算每个块 所占的大小
         */
        float itemSize = (360 * 1.0f - mCount * mSplitSize) / mCount;

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        mPaint.setColor(mFirstColor);
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval, (i * (itemSize + mSplitSize))-90, itemSize, false, mPaint);
        }

        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, (i * (itemSize + mSplitSize))-90, itemSize, false, mPaint);
        }
    }
}
