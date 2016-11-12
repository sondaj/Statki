package com.example.sebastian.statki;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Sebastian on 2016-06-06.
 */
public class Statek extends ImageView {
    public int x;
    public int y;
    public int length;
    public int cell;
    private int state;
    public boolean rotated;
    private String []colors;

    public Statek(Context cont, int leng, int xx, int yy, int cel){
        super(cont);

        x = xx;
        y = yy;
        length = leng;
        cell = cel;

        state = 1;

        colors = new String[4];
        colors[0] = "#550ff0f0";
        colors[1] = "#7700ff00";
        colors[2] = "#77ff0000";
        colors[3] = "#77ffff00";

        //setting image resource
        this.setImageResource(R.drawable.ship1);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((leng*(cell-2))-8, (cell-2)-8);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

        this.setLayoutParams(layoutParams);

        setSelect();

        setPosition(0,0);
    }

    public void setSize(int w, int h){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.getLayoutParams();

        layoutParams.width = (w*(cell-2))-8;
        layoutParams.height = (h*(cell-2))-8;

        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((xt*(cell-2))-8, (yt*(cell-2))-8);

        this.setLayoutParams(layoutParams);
    }

    public void setPosition(int xt, int yt){
        x = xt;
        y = yt;

        int xx = (int)(((float)cell-2)*(float)x);
        int yy = (int)(((float)cell-2)*(float)y);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
        layoutParams.setMargins(xx + 4, yy + 6, 0, 0);
        this.setLayoutParams(layoutParams);
    }

    public void setColor(String c){
        this.setBackgroundColor(Color.parseColor(c));
    }

    public void setColor(int n){
        this.setBackgroundColor(Color.parseColor(colors[n]));
    }

    public void setSelect(){
        setColor(3);
    }

    public void setUnselect(){
        setColor(state);
    }

    public void ustawStan(int s){
        state = s;
        setColor(s);
    }

    public void obrot(){
        if(rotated){
            setSize(length, 1);
            rotated = false;
        }else{
            setSize(1, length);
            rotated = true;
        }
    }
}
