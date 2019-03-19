package com.sdk.ads.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;

import com.sdk.ads.manager.AdsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushToServer extends AsyncTask<Void, Void, Boolean> {

    private String link = "https://0x612u1huh.execute-api.ap-southeast-1.amazonaws.com/apigetads/apigetads";
    private Context context;


    public PushToServer(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        String jsonString;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(link);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            String params = "{\"id_device\" : \"" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) + "\",\"package_name\" : \"" + context.getPackageName() + "\"}";
            writer.write(params);

            writer.flush();
            writer.close();
            os.close();

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

        jsonString = response.toString();

        try {
            JSONObject jsonResponse = new JSONObject(jsonString);
            String mess = jsonResponse.getString("message");
            return mess.equals("request done");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        AdsManager.getInstance().savePushToServer(aBoolean);
    }
}
