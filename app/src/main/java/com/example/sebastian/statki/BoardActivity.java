package com.example.sebastian.statki;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Random;

public class BoardActivity extends AppCompatActivity {
    private Engine engine;
    private View mContentView;
    private BoardClass b1;
    private BoardClass b2;
    private int nm = 0;
    private Statek[] statki;
    private boolean dragging;
    private int stateknr;
    public int tryb = 1;
    public boolean singleplayer = true;
    public int licz1;
    public int licz2;
    public int sumToWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        licz1 = 0;
        licz2 = 0;

        statki = new Statek[5];

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        setContentView(R.layout.activity_board);

        mContentView = findViewById(R.id.layout_boards);

        int width = getIntent().getExtras().getInt("width");
        int height = getIntent().getExtras().getInt("height");

        if (height > width) {
            int ww = width;
            width = height;
            height = ww;
        }

        int nw = width - height - 30;

        RelativeLayout bt1 = (RelativeLayout) findViewById(R.id.BoardPlatR1);
        RelativeLayout bt2 = (RelativeLayout) findViewById(R.id.BoardPlatR2);

        b1 = new BoardClass(height, (RelativeLayout) findViewById(R.id.BoardPlatR1), (TableLayout) findViewById(R.id.BoardPlat1));
        b2 = new BoardClass(height, (RelativeLayout) findViewById(R.id.BoardPlatR2), (TableLayout) findViewById(R.id.BoardPlat2));

        b1.pivotx = 0;
        b2.pivotx = 1;

        engine = new Engine(b1, b2);

        final ImageView shp1 = (ImageView) findViewById(R.id.b_ship_s1);
        final ImageView shp2 = (ImageView) findViewById(R.id.b_ship_s2);
        final ImageView shp3 = (ImageView) findViewById(R.id.b_ship_s3);
        final ImageView shp4 = (ImageView) findViewById(R.id.b_ship_s4);
        final ImageView shp5 = (ImageView) findViewById(R.id.b_ship_s5);

        Button btmnh1 = (Button) findViewById(R.id.b_place);
        Button btmnh2 = (Button) findViewById(R.id.b_obrot);
        Button btmnh3 = (Button) findViewById(R.id.b_start);

        btmnh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statki[stateknr] != null && tryb == 1) {
                    statki[stateknr].obrot();
                }
            }
        });
        btmnh3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statki[4] != null) {
                    tryb2();
                }
            }
        });

        shp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {createShip(1); shp1.setVisibility(View.INVISIBLE);}});
        shp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {createShip(2); shp2.setVisibility(View.INVISIBLE);}});
        shp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {createShip(3); shp3.setVisibility(View.INVISIBLE);}});
        shp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {createShip(4); shp4.setVisibility(View.INVISIBLE);}});
        shp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {createShip(5); shp5.setVisibility(View.INVISIBLE);}});

        for (int y = 0; y < b1.TABLE_HEIGHT; y++) {
            for (int x = 0; x < b1.TABLE_WIDTH; x++) {
                final int a = x;
                final int b = y;
                b1.buttons[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectField(a, b);
                    }
                });
            }
        }
    }

    public void tryb2() {
        sumToWin = 0;

        for (int i = 0; i < statki.length; i++) {
            if (statki[i] != null) {
                statki[i].ustawStan(0);
                statki[i].setOnClickListener(null);
                b1.ustaw(statki[i], 1);

                sumToWin += statki[i].length;
            }
        }
        tryb = 2;

        b2.show();
        engine.switchBoard();

        if(singleplayer){
            b2.randStatki(5);
        }

        findViewById(R.id.lay_rt_board2).setVisibility(View.GONE);
        findViewById(R.id.lay_rt_board3).setVisibility(View.GONE);

        for (int y = 0; y < b1.TABLE_HEIGHT; y++) {
            for (int x = 0; x < b1.TABLE_WIDTH; x++) {
                final int a = x;
                final int b = y;
                b1.buttons[x][y].setOnClickListener(null);
            }
        }
        for (int y = 0; y < b2.TABLE_HEIGHT; y++) {
            for (int x = 0; x < b2.TABLE_WIDTH; x++) {
                final int a = x;
                final int b = y;
                b2.buttons[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        strzal(a, b);
                    }
                });
            }
        }
    }

    public void strzal(int x, int y) {
        if(tryb == 2) {
            int val = b2.getPole(x, y);

            if(b2.moznaStrzelac(x, y)){
                if(b2.strzel(x, y)){
                    licz1++;
                    sprawdzCzyKoniec();
                }else {
                    tryb3();
                }
            }
        }
    }

    public void strzalO() {
        if(tryb == 3) {
            Random rd = new Random();

            int x = rd.nextInt(b1.TABLE_WIDTH);
            int y = rd.nextInt(b1.TABLE_HEIGHT);

            while(!b1.moznaStrzelac(x, y)){
                x = rd.nextInt(b1.TABLE_WIDTH);
                y = rd.nextInt(b1.TABLE_HEIGHT);
            }

            if(b1.strzel(x, y)){
                licz2++;
                sprawdzCzyKoniec();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                strzalO();
                            }
                        },
                        1000);
            }else {

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                engine.switchBoard();
                            }
                        },
                        500);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                tryb = 2;
                            }
                        },
                        1500);
            }
        }
    }

    public void tryb3() {
        tryb = 3;

        engine.switchBoard();

        if(singleplayer){
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            strzalO();
                        }
                    },
                    1500);
        }
    }

    public void sprawdzCzyKoniec() {
        if(licz1 >= sumToWin || licz2 >= sumToWin){
            tryb = 4;

            Intent i = new Intent(getApplicationContext(), FinishGameActivity.class);
            i.putExtra("licz1", licz1);
            i.putExtra("licz2", licz2);
            i.putExtra("sumToWin", sumToWin);
            startActivity(i);
        }
    }
    public void selectField(int x, int y) {
        if(statki[stateknr] != null && tryb == 1) {
            statki[stateknr].setPosition(x, y);
        }
    }

        @Override
        public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    public void createShip(int leng) {
        final Statek s1 = new Statek(this, leng, 0, 0, b1.cell);
        final int stateknx = nm;
        statki[nm++] = s1;

        wybierzStatek(stateknx);

        RelativeLayout rtv = (RelativeLayout) findViewById(R.id.BoardPlatR1);
        rtv.addView(s1);

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wybierzStatek(stateknx);
            }
        });
    }

    public void odznaczStatki() {
        for (int i = 0; i < statki.length; i++) {
            if (statki[i] != null) {
                statki[i].setUnselect();
            }
        }
    }

    public void wybierzStatek(int a) {
        odznaczStatki();

        stateknr = a;

        statki[a].bringToFront();
        statki[a].setSelect();
    }
}
