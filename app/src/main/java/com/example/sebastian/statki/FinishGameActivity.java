package com.example.sebastian.statki;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FinishGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);

        Bundle extras = getIntent().getExtras();

        int masz1 = extras.getInt("licz1");
        int masz2 = extras.getInt("licz2");
        int masz3 = extras.getInt("sumToWin");

        TextView t1 = (TextView) findViewById(R.id.wynik_tekst);
        TextView t2 = (TextView) findViewById(R.id.wynik_liczba_1);
        TextView t3 = (TextView) findViewById(R.id.wynik_liczba_2);

        if(masz1 > masz2){
            t1.setText("Wygrałeś!");
        }else if(masz1 < masz2){
            t1.setText("Przegrałeś!");
        }else{
            t1.setText("Remis!");
        }

        t2.setText(masz1+"");
        t3.setText(masz2+"");

        Button bt1 = (Button) findViewById(R.id.button7);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}
