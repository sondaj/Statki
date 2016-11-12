package com.example.sebastian.statki;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sebastian on 2016-06-06.
 */
public class GameEngine {
    private Pole[][] map;
    private int[][] mapE;
    public Statek[] statki;
    public float cellSize;
    public float width;
    public int margin;
    public int choosen;
    public int stan;
    private float bx;
    private float by;
    private float ofx;
    private float ofy;
    private float pfx;
    private float pfy;
    public Przycisk[] przyciski;
    private Context context;

    public GameEngine(int x, int y, int m, Context ccc) {
        /*ofx = ofy = 0;
        stan = 0;

        margin = m;
        context = ccc;
        choosen = -1;

        map = new Pole[10][10];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = new Pole(i, j);
            }
        }

        mapE = new int[10][10];

        int rozmiar = Math.min(x, y) - (margin * 2);
        cellSize = rozmiar / Math.max(map.length, map[0].length);

        statki = new Statek[4];

        statki[0] = new Statek(this, 1);
        statki[1] = new Statek(this, 2);
        statki[2] = new Statek(this, 3);
        statki[3] = new Statek(this, 4);

        float vv = x - (cellSize * 6);
        float bb = margin;

        for (int i = 0; i < statki.length; i++) {
            statki[i].x = vv;
            statki[i].y = bb;

            bb += statki[i].height() + 10;
        }

        przyciski = new Przycisk[3];
        przyciski[0] = new Przycisk();
        przyciski[1] = new Przycisk();
        przyciski[2] = new Przycisk();

        float www = (x - (map.length * cellSize) - (2 * margin) - 20) / 3;

        float xx = x - www - margin + 60;
        float yy = y - www - margin - 9;

        przyciski[2].x = xx;
        przyciski[1].x = przyciski[2].x - www - 20;
        przyciski[0].x = przyciski[1].x - www - 20;

        przyciski[0].y = przyciski[1].y = przyciski[2].y = yy;

        przyciski[0].width = przyciski[1].width = przyciski[2].width = www;
        przyciski[0].height = przyciski[1].height = przyciski[2].height = www;*/
    }

    /*
    public void mouseUp() {
        if (choosen >= statki.length) {
            choosen = -1;
        }
        Log.d("wybrany", choosen + "");

        if (choosen >= 0) {
            float cx = bx * cellSize + margin;
            float cy = by * cellSize + margin;

            statki[choosen].x = cx;
            statki[choosen].y = cy;

            if (!ustawStatek(choosen, (int) bx, (int) by)) {
                statki[choosen].x = pfx;
                statki[choosen].y = pfy;
            }
        }

        choosen = -1;
    }

    public void mouseMove(float x, float y) {
        bx = Math.round((x - ofx - margin) / cellSize);
        by = Math.round((y - ofy - margin) / cellSize);

        if (choosen >= 0) {
            statki[choosen].x = x - ofx;
            statki[choosen].y = y - ofy;
        }
    }

    public void mouseDown(float x, float y) {
        if (stan == 0) {
            for (int i = 0; i < statki.length; i++) {
                if (statki[i].touched(x, y)) {
                    choosen = i;
                    statki[i].placed = false;

                    Log.d("wybrany", choosen + "," + statki[i].x + "," + statki[i].y);

                    pfx = statki[i].x;
                    pfy = statki[i].y;

                    ofx = x - statki[i].x;
                    ofy = y - statki[i].y;
                }
            }

            if (przyciski[0].touched(x, y)) {
                losoweRozmieszczenie();
            }


            if (przyciski[1].touched(x, y)) {
                boolean wrong = false;

                for (int i = 0; i < statki.length; i++) {
                    if (!statki[i].placed) {
                        wrong = true;
                    }
                }
                if (wrong) {
                    Toast.makeText(context, "Rozmieść wszystkie statki", Toast.LENGTH_LONG).show();
                } else {
                    statkiWroga();

                    stan = 1;
                }
            }
        } else if (stan == 1) {
            int xx = (int) Math.floor((x - margin) / cellSize);
            int yy = (int) Math.floor((y - margin) / cellSize);

            if (strzel(xx, yy, 0)) {

                stan = 2;

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                strzalWroga();
                            }
                        },
                        20);
            }
        }
    }

    public boolean strzel(int x, int y, int k) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            return false;
        }

        Pole p = map[x][y];

        if (k == 0) {
            if (p.fired1) {
                return false;
            }

            p.fired1 = true;
        } else {
            if (p.fired2) {
                return false;
            }

            p.fired2 = true;
        }

        int v = map[x][y].v();
        int w = mapE[x][y];


        Log.d("strzal " + k, v + "");

        return true;
    }

    public void statkiWroga() {
        czyscMapeE();

        for (int i = 0; i < 10; i++) {
            mapE[((int) Math.round(Math.random() * (mapE.length - 1)))][((int) Math.round(Math.random() * (mapE[0].length - 1)))] = 1;
            map[((int) Math.round(Math.random() * (mapE.length - 1)))][((int) Math.round(Math.random() * (mapE[0].length - 1)))].statek2 = true;
        }
    }

    public void strzalWroga() {
        int xr = ((int) Math.round(Math.random() * (map.length - 1)));
        int yr = ((int) Math.round(Math.random() * (map[0].length - 1)));

        if (!strzel(xr, yr, 1)) {
            strzalWroga();

            return;
        }

        stan = 1;
    }

    public void czyscMape() {
        for (int g = 0; g < map.length; g++) {
            for (int k = 0; k < map[0].length; k++) {
                map[g][k].v(0);
            }
        }
    }

    public void czyscMapeE() {
        for (int g = 0; g < mapE.length; g++) {
            for (int k = 0; k < mapE[0].length; k++) {
                mapE[g][k] = 0;
            }
        }
    }

    public void statkiAktualizuj(Canvas can) {
        if (choosen >= 0) {
            float cx = bx * cellSize + margin;
            float cy = by * cellSize + margin;

            if (cx < 0 || cx >= map.length || cy < 0 || cy >= map[0].length) {
                return;
            }

            Paint pt = new Paint();
            pt.setStyle(Paint.Style.FILL);
            pt.setColor(Color.parseColor("#b39c98"));

            can.drawRect(new RectF(cx, cy, cx + statki[choosen].width(), cy + statki[choosen].height()), pt);

            //statki[choosen].x = 1000;
        }
    }

    public Pole[][] getMap() {
        return map;
    }

    public void draw(Canvas can) {
        statkiAktualizuj(can);

        for (int i = 0; i < przyciski.length; i++) {
            przyciski[i].draw(can);
        }

        for (int i = 0; i < statki.length; i++) {
            statki[i].draw(can);
        }
    }

    public void wyczyscStatki() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j].statek1 = false;
            }
        }

        for (int g = 0; g < statki.length; g++) {
            if (statki[g].placed) {
                for (int m = 0; m < statki[g].widthM(); m++) {
                    for (int n = 0; n < statki[g].heightM(); n++) {
                        int xx = (int) Math.floor((statki[g].x - margin) / cellSize);
                        int yy = (int) Math.floor((statki[g].y - margin) / cellSize);

                        Log.d("stat", statki[g].placed + "," + statki[g].x + "," + statki[g].y + "," + margin);
                        Log.d("poz", xx + "," + yy);

                        map[xx + m][yy + n].statek1 = true;
                    }
                }
            }
        }
    }

    public boolean ustawStatek(int i, int x, int y) {
        boolean ok = true;

        for (int g = 0; g < statki[i].widthM(); g++) {
            for (int k = 0; k < statki[i].heightM(); k++) {
                if (map[x + g][y + k].statek1) {
                    ok = false;
                }
            }
        }

        if (ok) {
            statki[i].x = x * cellSize + margin;
            statki[i].y = y * cellSize + margin;

            statki[i].placed = true;

            for (int g = 0; g < statki[i].widthM(); g++) {
                for (int k = 0; k < statki[i].heightM(); k++) {
                    map[x + g][y + k].statek1 = true;
                }
            }

            wyczyscStatki();

            return true;
        }

        return false;
    }

    public void losoweRozmieszczenie() {
        //czyscMape();

        for (int i = 0; i < statki.length; i++) {
            int ww = (int) Math.round((map.length - statki[i].widthM()) * Math.random());
            int hh = (int) Math.round((map[0].length - statki[i].heightM()) * Math.random());

            if (!ustawStatek(i, ww, hh)) {
                i--;
            }
        }
    }
    */
}
