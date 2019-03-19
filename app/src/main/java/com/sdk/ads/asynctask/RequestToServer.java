package com.sdk.ads.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sdk.ads.manager.AdsManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestToServer extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private String urlRequest = "https://0x612u1huh.execute-api.ap-southeast-1.amazonaws.com/apigetads/apigetads";

    public RequestToServer(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String json;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlRequest);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.connect();

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        json = response.toString();
        Log.d("datdb", "json: " + json);
        return AdsManager.getInstance().parseAdsModel(json, true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            AdsManager.getInstance().parseDone();
        }
    }
}
