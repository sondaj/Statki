package com.example.sebastian.statki;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class gameType extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_type);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        Button bt1 = (Button) findViewById(R.id.menu_btn_1_1);
        Button bt2 = (Button) findViewById(R.id.menu_btn_1_2);

        Efekt.buttonEffect(bt1);
        Efekt.buttonEffect(bt2);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String token = sharedPref.getString("token", "");

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), FullscreenActivity2.class));
                //startActivity(new Intent(getApplicationContext(), BoardActivity.class));

                RelativeLayout f = (RelativeLayout) findViewById(R.id.gametype_layout);
                Intent i = new Intent(getApplicationContext(), BoardActivity.class);
                i.putExtra("width", f.getMeasuredWidth());
                i.putExtra("height", f.getMeasuredHeight());
                startActivity(i);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(token.length() > 0){// && false){
                    startActivity(new Intent(getApplicationContext(), gameList.class));
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(gameType.this);

                    builder.setMessage(R.string.dialog_login_text)
                            .setPositiveButton(R.string.dialog_login_login, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(getApplicationContext(), loginActivity.class);
                                    i.putExtra("redirect","listaGier");
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton(R.string.dialog_login_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //startActivity(new Intent(getApplicationContext(), gameType.class));
                                }
                            });

                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
