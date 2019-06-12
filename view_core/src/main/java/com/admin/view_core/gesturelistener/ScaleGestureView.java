package com.admin.view_core.gesturelistener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * @author Lv
 * Created at 2019/6/12
 */
public class ScaleGestureView extends View {

    private ScaleGestureDetector mScaleGesture;

    public ScaleGestureView(Context context) {
        super(context);
    }

    public ScaleGestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScaleGesture();
    }

    private void initScaleGesture() {
        mScaleGesture = new ScaleGestureDetector(getContext(),
                new ScaleGestureDetector.SimpleOnScaleGestureListener() {

                    // 缩放手势开始，当两个手指放在屏幕上会调用该方法(值调用一次)
                    // 返回 false 表示不使用当前这次缩放手势
                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        return true;
                    }

                    //缩放被触发(会被调用 0 次 或者多次)，返回 true 表示缩放事件已经被处理
                    // 检测器会重新积累缩放因子，返回false 会继续基类缩放因子
                    @Override
                    public boolean onScaleBegin(ScaleGestureDetector detector) {
                        return true;
                    }

                    //缩放手势结束
                    @Override
                    public void onScaleEnd(ScaleGestureDetector detector) {

                    }
                });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGesture.onTouchEvent(event);
        return true;
    }
}
