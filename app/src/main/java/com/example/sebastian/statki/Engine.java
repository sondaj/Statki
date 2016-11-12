package com.example.sebastian.statki;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by Sebastian on 2016-10-24.
 */
public class Engine {
    public int current = 2;
    private BoardClass btm1;
    private BoardClass btm2;
    private int state = 0;

    public Engine(BoardClass b1, BoardClass b2){
        btm1 = b1;
        btm2 = b2;

        //switchBoard();

        btm2.hide();
    }

    public void switchBoard(){
        if(current == 1){
            minimize(btm1);
            maximize(btm2);
            current = 2;
        }else{
            maximize(btm1);
            minimize(btm2);
            current = 1;
        }
    }
    public void maximize(BoardClass v){
        ScaleAnimation scale = new ScaleAnimation(
                1f, 0.5f,
                1f, 0.5f,
                Animation.RELATIVE_TO_SELF, v.pivotx,
                Animation.RELATIVE_TO_SELF, 1);
        scale.setFillAfter(true);
        scale.setDuration(1000);
        scale.setFillBefore(true);
        scale.setFillEnabled(true);
        v.tableR.startAnimation(scale);
    }
    public void minimize(BoardClass v){
        ScaleAnimation scale = new ScaleAnimation(
                0.5f, 1f,
                0.5f, 1f,
                Animation.RELATIVE_TO_SELF, v.pivotx,
                Animation.RELATIVE_TO_SELF, 1);
        scale.setFillAfter(true);
        scale.setDuration(1000);
        scale.setFillBefore(true);
        scale.setFillEnabled(true);
        v.tableR.startAnimation(scale);
    }
}
