package com.admin.customview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.admin.view_core.view.CustomVolumeControlBar;

/**
 * @author Lv
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private int height;
    private Button btn3;

    private boolean flag = true;
    private GestureDetector mGestureDetector;
    private static final String TAG = "MainActivity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建 gestureDetector 类的 静态内部类来实现，可以重写任意的方法
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
            final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //小例子：判断是左滑 还是右滑
                if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
                    Log.e(TAG, "onFling: 左滑"+e1.getX()+"------"+e2.getX() );
                }else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
                    Log.e(TAG, "onFling: 右滑 "+e1.getX()+"------"+e2.getX()  );
                }
                return false;
            }
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.e(TAG, "onDoubleTapEvent: 双击" );
                return false;
            }
        });

        CustomVolumeControlBar controlBar = findViewById(R.id.controlBar);
        //触摸事件
        controlBar.setOnTouchListener(this);
        //获取焦点
        controlBar.setFocusable(true);
        //启用或者禁用 单击事件
        controlBar.setClickable(true);
        //启用或者禁用 长按事件
        controlBar.setLongClickable(true);

    }

    /**
     * 通过调用 GestureDetector 的 onTouchEvent()方法，将捕捉到的 MotionEvent 交给 GestureDetector
     * 来分析 是否由合适的 callback 函数来处理用户的手势
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
