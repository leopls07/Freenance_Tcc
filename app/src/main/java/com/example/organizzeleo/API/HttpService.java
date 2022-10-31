package com.example.organizzeleo.API;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class HttpService  extends AsyncTask<Void, Void,Resposta> {

    private final String coin;

    public HttpService(String coin) {
        this.coin = coin;
    }

    protected Resposta doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        if (this.coin !=null) {
            try {
                URL url = new URL("https://www.mercadobitcoin.net/api/"+coin+"/ticker/");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    resposta.append(scanner.next());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new Gson().fromJson(resposta.toString(), Resposta.class);

    }


}



