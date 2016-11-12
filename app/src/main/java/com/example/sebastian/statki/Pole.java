package com.example.sebastian.statki;

/**
 * Created by Sebastian on 2016-06-08.
 */
public class Pole {
    public boolean fired1;
    public boolean fired2;
    public boolean statek1;
    public boolean statek2;
    public int x;
    public int y;
    private int value;

    public Pole(int xx, int yy){
        x = xx;
        y = yy;

        fired1 = fired2 = false;
        statek1 = statek2 = false;
    }

    public int v(){
        String s = "1";

        s += fired1 ? "1" : "0";
        s += fired2 ? "1" : "0";
        s += statek1 ? "1" : "0";
        s += statek2 ? "1" : "0";

        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException nfe) {
            return 10000;
        }
    }

    public void v(int vv){
        if(vv == 0){
            fired1 = fired2 = false;
            statek1 = statek2 = false;
        }else if(vv == 1){
            statek1 = true;
        }else if(vv == 2){
            statek2 = true;
        }

        value = vv;
    }
}
