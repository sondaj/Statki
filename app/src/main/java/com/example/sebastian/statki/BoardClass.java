package com.example.sebastian.statki;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Sebastian on 2016-10-24.
 */
public class BoardClass {
    private int[][] strzal;
    private int[][] map;
    public ImageButton[][] buttons;
    public int pivotx;
    public TableLayout table;
    public RelativeLayout tableR;
    public Statek []statky;
    public static final int TABLE_WIDTH = 10;
    public static final int TABLE_HEIGHT = 10;
    public int cell;
    private int nm = 0;

    public BoardClass(int size, RelativeLayout rtab, TableLayout tab) {
        strzal = new int[TABLE_WIDTH][TABLE_HEIGHT];
        map = new int[TABLE_WIDTH][TABLE_HEIGHT];
        buttons = new ImageButton[TABLE_WIDTH][TABLE_HEIGHT];

        cell = (size-20)/10;
        table = tab;
        tableR = rtab;

        // Populate the table with stuff
        for (int y = 0; y < TABLE_HEIGHT; y++) {
            final int row = y;
            TableRow r = new TableRow(table.getContext());
            table.addView(r);
            for (int x = 0; x < TABLE_WIDTH; x++) {
                final int col = x;
                ImageButton b = new ImageButton(table.getContext());
                b.setImageResource(R.drawable.title);
                b.setLayoutParams(new TableRow.LayoutParams(cell - 2, cell - 2));
                b.setPadding(2, 2, 0, 0);

                buttons[x][y] = b;
                map[x][y] = 0;
                r.addView(b);
            }
        }
    }

    public boolean randStatek(int len){
        Random r = new Random();

        int xx, yy;

        boolean wolne = true;

        boolean rotat = r.nextBoolean();

        if(rotat){
            xx = r.nextInt(TABLE_WIDTH);
            yy = r.nextInt(TABLE_HEIGHT - len);

           for (int m = -1; m < 1; m++) {
                for (int h = -1; h < len+1; h++) {
                    if (getPole(xx + m, yy + h) != 0) {
                        wolne = false;
                    }
                }
            }
        }else{
            xx = r.nextInt(TABLE_WIDTH - len);
            yy = r.nextInt(TABLE_HEIGHT);

            for (int m = -1; m < 1; m++) {
                for (int h = -1; h < len+1; h++) {
                    if (getPole(xx + h,yy + m) != 0) {
                        wolne = false;
                    }
                }
            }
        }

        if(!wolne){
            return randStatek(len);
        }else{
            if(rotat) {
                for (int h = 0; h < len; h++) {
                    ustaw(xx, yy + h, 1);
                }
            }else{
                for (int h = 0; h < len; h++) {
                    ustaw(xx+ h, yy, 1);
                }
            }
        }

        return wolne;
    }

    public void randStatki(int max){
        for(int i=max;i > 0; i--){
            randStatek(i);
        }
    }

    public boolean strzel(int x, int y){
        if(moznaStrzelac(x,y)){
            strzal[x][y] = 1;
            ustaw(x, y, 3);

            if(getPole(x,y) == 1){
                return true;
            }
        }
        return false;
    }

    public boolean moznaStrzelac(int x, int y){ if(strzal[x][y] == 0){return true;} return false; }
    public int getPole(int x, int y){
        if(x < 0 || x >= TABLE_WIDTH || y < 0 || y >= TABLE_HEIGHT){
            return 0;
        }

        return map[x][y];
    }
    public void ustaw(int x, int y, int v){
        if(v == 1) {//ustaw 1 czyli statek
            map[x][y] = v;
            buttons[x][y].setBackgroundColor(Color.parseColor("#00ff00"));
        }else if(v == 3){//ustaw 3 czyli strzal
            if(getPole(x,y) == 0) {//nie ma statku [pudlo]
                buttons[x][y].setImageResource(R.drawable.tile_empty);
            }else{//jest statek [trafiony]
                buttons[x][y].setImageResource(R.drawable.tile_fired);
            }
        }
    }

    public void ustaw(Statek s, int v){
        //statky[nm++] = s;
        if(s.rotated) {
            for (int h = 0; h < s.length; h++) {
                ustaw(s.x, s.y + h, v);
            }
        }else{
            for (int h = 0; h < s.length; h++) {
                ustaw(s.x + h, s.y, v);
            }
        }
    }

    public void ustaw(Statek []s, int v){
        statky = s;
        for (int h = 0; h < s.length; h++) {
            ustaw(s[h], v);
        }
    }

    public void hide(){
        tableR.setVisibility(View.INVISIBLE);
    }

    public void show(){
        tableR.setVisibility(View.VISIBLE);
    }
}
