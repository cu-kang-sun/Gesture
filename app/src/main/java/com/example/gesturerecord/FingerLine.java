package com.example.gesturerecord;

import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FingerLine extends View {
    private final Paint paint;
    public List<Point> points;
    private boolean paintingMode;


    public FingerLine(Context context) {
        this(context, null);
    }

    public FingerLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintingMode = true;
        points = new ArrayList<Point>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);
    }

    @Override protected void onDraw(Canvas canvas) {
        if(paintingMode == false)
            return;
        paint.setColor(Color.WHITE);


        Path path = new Path();
        if (points.size() > 1) {
            Point prevPoint = null;
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);

                if (i == 0) {
                    path.moveTo(point.x, point.y);
                } else {
                    float midX = (prevPoint.x + point.x) / 2;
                    float midY = (prevPoint.y + point.y) / 2;

                    if (i == 1) {
                        path.lineTo(midX, midY);
                    } else {
                        path.quadTo(prevPoint.x, prevPoint.y, midX, midY);
                    }
                }
                prevPoint = point;

            }
            path.lineTo(prevPoint.x, prevPoint.y);
        }

        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if(paintingMode == false)
            return false;

        if(event.getAction() != MotionEvent.ACTION_UP){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                points = new ArrayList<>();
            }
            Point point = new Point(event.getX(), event.getY());
            points.add(point);
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }




    public List<Point> getPoints(){
        return this.points;
    }


}