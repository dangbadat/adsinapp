package com.sdk.ads.asynctask;

import android.os.AsyncTask;

import com.sdk.ads.manager.AdsManager;

public class ParseDataSave extends AsyncTask<Void, Void, Boolean> {

    private String json;

    public ParseDataSave(String json) {
        this.json = json;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return AdsManager.getInstance().parseAdsModel(json, false);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            AdsManager.getInstance().parseDone();
        }
    }
}
