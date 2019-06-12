package com.admin.view_core.gesturelistener;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * @author Lv
 * Created at 2019/6/12
 */
public class GestureListener implements GestureDetector.OnGestureListener ,
    GestureDetector.OnDoubleTapListener{

    private static final String TAG = "GestureListener";

    // 用户按下屏幕就会触发；
    @Override
    public boolean onDown(MotionEvent e) {
        Log.e(TAG, "onDown: 按下屏幕就会触发" );
        return false;
    }

    //用户按下屏幕，尚未松开或 者拖动 则会触发
    @Override
    public void onShowPress(MotionEvent e) {
        Log.e(TAG, "onShowPress: 按下屏幕，尚未松开或 者拖动" );
    }

    // 用户点击屏幕后松开，则会触发该事件
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.e(TAG, "onSingleTapUp: 点击屏幕后松开" );
        return false;
    }


    /**
     * 用户按下屏幕，并拖动

     * @param distanceX x 轴滑动距离
     * @param distanceY y 轴滑动距离
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.e(TAG, "onScroll: 用户按下屏幕，并拖动" );
        return false;
    }

    // 用户长按屏幕，
    @Override
    public void onLongPress(MotionEvent e) {
        Log.e(TAG, "onLongPress:长按屏幕 " );
    }

    final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
    /**
     * 用户按下屏幕，快速移动后松开，
     * @param e1 按下的 event
     * @param e2 抬起的 event
     * @param velocityX x 轴上的移动速度 像素 每秒
     * @param velocityY y 轴上的移动速度 像素 每秒
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e(TAG, "onFling: 用户按下屏幕，快速移动后松开，" );

        //小例子：判断是左滑 还是右滑
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Log.e(TAG, "onFling: 左滑"+e1.getX()+"------"+e2.getX() );
        }else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            Log.e(TAG, "onFling: 右滑 "+e1.getX()+"------"+e2.getX()  );
        }
        return false;
    }



    //单击事件，用来判读该点击 是 singleTap，
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.e(TAG, "onSingleTapConfirmed: 点击" );
        return false;
    }

    // 双击 事件
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.e(TAG, "onDoubleTap: 双击" );
        return false;
    }
    // 双击 间隔中发生的动作
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.e(TAG, "onDoubleTapEvent: 双击中的间隔" );
        return false;
    }

}
