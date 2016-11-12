package com.example.sebastian.statki;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {
    Constant c =new Constant();
    //c.
    private static final String HOST = "http://redost.pl/";

    public String redirect;
    public InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            redirect = extras.getString("redirect");
            Log.d("red1", redirect);
        }

        Button bt1 = (Button) findViewById(R.id.btn_lactivity_login);

        Efekt.buttonEffect(bt1);

        EditText et1 = (EditText) findViewById(R.id.edLogin);

        et1.requestFocus();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logowanie();
            }
        });
    }

    public void wroc(){
        Log.d("red2", redirect);
        Log.d("red2", "" + (redirect.equals("listaGier")));
        if(redirect.equals("listaGier")){
            startActivity(new Intent(getApplicationContext(), gameList.class));
        }else{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
    }

    public void logowanie(){
        EditText et1 = (EditText) findViewById(R.id.edLogin);
        EditText et2 = (EditText) findViewById(R.id.edHaslo);

        String l = et1.getText().toString();
        String h = et2.getText().toString();

        Request req = new Request(HOST+"login", "login="+l+"&haslo="+h);
        req.setResult(new ReqRes1(req));
        req.send();
    }

    class ReqRes1 extends RequestResult {
        public ReqRes1(Request r){
            super(r);
        }

        public void onComplete(){
            try {
                String token = request.JSONresponse.getString("token");

                if(token.length() > 0) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", token);
                    editor.commit();

                    wroc();
                }

                Log.d("Wynik", request.JSONresponse.getString("token"));
            }catch(Exception e){

            }
        }
    }
}
