package com.admin.view_core.viewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Path.Direction.CW;

/**
 * @author Lv
 * Created at 2019/5/26
 */
public class Text extends View {

    int TextX = 50;

    public int getTextX() {
        return TextX;
    }

    public void setTextX(int textX) {
        TextX = textX;
        invalidate();
    }

    public Text(Context context) {
        super(context);
    }

    public Text(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);//描边
        paint.setStrokeCap(Paint.Cap.BUTT); //线帽：默认
        paint.setStrokeJoin(Paint.Join.BEVEL); //直线

        paint.setTextSize(50);
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//字体类型
        //对齐方式
        paint.setTextAlign(Paint.Align.LEFT);


        Path path = new Path();
        path.addCircle(100,100, 100,CW);

        canvas.drawPath(path,paint);

    }
}