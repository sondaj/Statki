package com.example.sebastian.statki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by Sebastian on 2016-04-16.
 */
public class Request extends AsyncTask<String, Void, Boolean> {
    public RequestResult wynik;

    public String urlp = "";
    public String data = "";
    public String response = "";
    public JSONObject JSONresponse;

    public Request(){
        super();
    }

    public Request(String u, String d){
        urlp = u;
        data = d;
    }

    public void setResult(RequestResult w){
        wynik = w;
    }

    public void send(){
        configData();

        this.execute(urlp, data);
    }

    public void configData(){
        String[] amb = data.split("&");

        String af = "";
        for (int i=0;i < amb.length;i++) {
            String[] ah = amb[i].split("=");

            String val = "";
            try {
                val = URLEncoder.encode(ah[1], "UTF-8");
            }catch (Exception e){
                val = ah[1];
            }

            af += ah[0] + "=" + val;

            if(i < amb.length-1) {
                af += "&";
            }
        }

        data = af;
    }

            @Override
            protected Boolean doInBackground(String... urls) {
            try {
                HttpURLConnection conn;

                URL url = new URL(urlp);
                String param = data;

                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setFixedLengthStreamingMode(param.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(param);
                out.close();

                Scanner inStream = new Scanner(conn.getInputStream());

                while (inStream.hasNextLine()) {
                    response += (inStream.nextLine());
                }

                JSONresponse = new JSONObject(response.toString());
            } catch (Exception e) {
                Log.d("BLAD", "" + e.getMessage());

                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            wynik.onComplete();
        }

        @Override
        protected void onCancelled() {

        }

    public void onComplete(){
        Log.d("BLAD", response);
    }
}
