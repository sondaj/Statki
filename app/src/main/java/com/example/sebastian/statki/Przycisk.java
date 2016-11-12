package com.example.sebastian.statki;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Sebastian on 2016-06-06.
 */
public class Przycisk {
    public float x;
    public float y;
    public float width;
    public float height;
    private Paint paint;

    public Przycisk(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.parseColor("#20222a"));
        paint.setStrokeWidth(2);
    }

    public void draw(Canvas can){
        can.drawRect(new RectF(x, y, x+width, y+height), paint);
    }

    public boolean touched(float xm, float ym){
        if(xm >= x && xm <= x + width && ym >= y && ym < y + height){
            return true;
        }

        return false;
    }
}
