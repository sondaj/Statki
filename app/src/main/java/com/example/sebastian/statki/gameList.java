package com.example.sebastian.statki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class gameList extends AppCompatActivity {
    private static final String HOST = "http://redost.pl/";

    private ListView list;
    private GameListAdapter adapter;
    private ArrayList<RowOneGame> listaGier;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        String a = getString(R.string.server);
        Log.d("adres", a);
        listaGier = new ArrayList<RowOneGame>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPref.getString("token", "");

        if(token.length() > 0){

            ladujListe();
        }else{

        }
    }

    public void ladujListe(){
        Request req = new Request(HOST+"gry", "token="+token);
        req.setResult(new ReqRes1(req));
        req.send();

        list = (ListView) findViewById(R.id.lista_gier);

        adapter = new GameListAdapter(this, listaGier);
        list.setAdapter(adapter);
    }

    class ReqRes1 extends RequestResult {
        public ReqRes1(Request r){
            super(r);
        }

        public void onComplete(){
            try {
                JSONArray arr = request.JSONresponse.getJSONArray("gry");

                listaGier.clear();

                for(int i = 0; i < arr.length(); i++){
                    //Log.d("wiersz", arr.getJSONObject(i).getString("title")+", "+arr.getJSONObject(i).getBoolean("occupied"));
                    listaGier.add(new RowOneGame(arr.getJSONObject(i).getString("title"), arr.getJSONObject(i).getBoolean("occupied")));
                }

                list.setAdapter(adapter);
            }catch(Exception e){

            }
        }
    }

    public void dupa(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    startActivity(new Intent(getApplicationContext(), gameType.class));

                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
