package com.admin.view_core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.admin.view_core.R;

/**
 * @author Lv
 * Created at 2019/6/4
 */
public class CustomImageView extends View {

    private static final int FILEXY = 0;
    private static final int CENTER = 1;

    private Bitmap mImage;
    private int mImageScale;
    private String mTitle;
    private int mTextColor;
    private int mTextSize;
    private Rect rect;
    private Paint mPaint;
    private Rect mTextBound;

    private int mWidth;
    private int mHeight;
    private TextPaint paint;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomImageView, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomImageView_image) {
                mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
            } else if (attr == R.styleable.CustomImageView_imageScaleType) {
                mImageScale = a.getInt(attr, CENTER);
            } else if (attr == R.styleable.CustomImageView_titleText) {
                mTitle = a.getString(attr);
            } else if (attr == R.styleable.CustomImageView_titleColor) {
                mTextColor = a.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.CustomImageView_titleTextSize) {
                mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            }
        }
        a.recycle();

        mPaint = new Paint();
        rect = new Rect();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        //计算字体的范围
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
         *  设置宽度
         */
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            //图片的 宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            //文字的 宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            if (widthMode == MeasureSpec.AT_MOST) {
                int desire = Math.max(desireByImg, desireByTitle);
                mWidth = Math.min(desire, widthSize);
            }
        }

        /*
         *  设置高度
         */
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desire, heightSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mTextBound.width() > mWidth) {
            if (paint == null) {
                paint = new TextPaint(mPaint);
            }
            String msg = TextUtils.ellipsize(mTitle, paint,
                    (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() / 2, (mHeight - getPaddingBottom()), mPaint);
        }

        //减去 字体的高度
        rect.bottom -= mTextBound.height();
        if (mImageScale == FILEXY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
    }

}
