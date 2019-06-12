package com.admin.view_core.viewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Lv
 * Created at 2019/6/11
 */
public class CustomContainer extends ViewGroup {

    public CustomContainer(Context context) {
        this(context, null);
    }

    public CustomContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //只需要 ViewGroup 能够支持margin 即可，那么直接使用 MarginLayoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算出所有的childView 的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //如果是 wrap_content 设置的宽和高
        int width = 0;
        int height = 0;

        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        //用于计算左边两个childView 的高度
        int lHeight = 0;
        //用于计算 右边两个childView 的高度，最终高度 区两者的最大值
        int rHeight = 0;

        //用于计算 上面两个 childView 的宽度
        int tWidth = 0;
        //用于计算 下面两个 childView 的宽度，最终宽度器取两者中的最大值
        int bWidth = 0;

        //根据 childView 计算出ViewGroup的宽和高，以及设置的margin 计算容器的 宽和高，主要容器是 warp_content时
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();
            //宽
            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            //高
            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }
            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

        }
        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);
        /*
         * 如果是 wrap_content 设置为我们计算的值
         * 否则 直接设置为 父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width,
                (heightMode == MeasureSpec.EXACTLY )? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        /*
         * 遍历 所有的childView ，根据childView的宽以及 margin，然后分别将 0,1,2,3 位置的View
         * 分别设置在四个角。
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth- cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                default:
                    break;
            }
            cr = cl+cWidth;
            cb = cHeight +ct;
            childView.layout(cl,ct,cr,cb);
        }
    }
}
