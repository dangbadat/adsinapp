package com.sdk.ads.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.sdk.ads.ads.FullScreenAds;
import com.sdk.ads.ads.MoreAppActivity;
import com.sdk.ads.ads.NativeAds;
import com.sdk.ads.asynctask.ParseDataSave;
import com.sdk.ads.asynctask.PushToServer;
import com.sdk.ads.asynctask.RequestToServer;
import com.sdk.ads.model.AppInfo;
import com.sdk.ads.model.ScreenShot;
import com.sdk.ads.utils.MethodUtils;
import com.sdk.ads.utils.TinyDB;
import com.sdk.ads.view.BannerAdmobView;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AdsManager {

    public static final String ACTION_ADS_LOADED = "action_ads_loaded";
    private static final String DATA_SAVE = "data_save";

    private static final String TIMES_SHOW_FULL = "times_show_full";
    private static final String TIMES_SHOW_NATIVE = "times_show_native";

    private static final String PUSH_TO_SERVER = "push_to_server";

    public static final String ADMOB_APP_ID = "admob_app_id";
    public static final String ADMOB_FULL_SCREEN_ID = "admob_full_screen_id";
    public static final String ADMOB_BANNER_ID = "admob_banner_id";
    public static final String ADMOB_NATIVE_ID = "admob_native_id";

    private int timesFull = 0;
    private int timesNative = 0;

    private static AdsManager instance;
    private static Context context;
    private static TinyDB tinyDB;


    private AppInfo appMain;
    private ArrayList<AppInfo> appInfos = new ArrayList<>();
    private ArrayList<AppInfo> appRecommended = new ArrayList<>();
    private ArrayList<AppInfo> appMayLike = new ArrayList<>();
    private ArrayList<AppInfo> appOnBackpress = new ArrayList<>();
    private ArrayList<AppInfo> appFulls = new ArrayList<>();
    private ArrayList<AppInfo> appNatives = new ArrayList<>();
    private ArrayList<AppInfo> appBanners = new ArrayList<>();
    private boolean isShowAdsMore = true;
    private boolean isShowAdsOnBackpress = true;

    private List<ApplicationInfo> applicationInfos;


    public static void init(Context ctx) {
        if (context == null) {
            context = ctx;
            tinyDB = new TinyDB(context);
        }
        if (instance == null) {
            instance = new AdsManager();
        }

        String jsonSave = tinyDB.getString(DATA_SAVE);
        if (!jsonSave.equals("")) {
            new ParseDataSave(jsonSave).execute();
        }
        if (MethodUtils.isNetworkConnected(context)) {
            new RequestToServer(context).execute();

            if (!instance.isPushToServer()) {
                new PushToServer(context).execute();
            }
        }
    }

    public static void initAdmob(Context context) {
        String admobId = tinyDB.getString(ADMOB_APP_ID);
        if (!admobId.equals("")) {
            AdMobManager.init(context, tinyDB.getString(ADMOB_APP_ID));
        }
    }

    public static AdsManager getInstance() {
        if (instance == null) {
            instance = new AdsManager();
        }
        return instance;
    }

    public void showAdmobFullScreen(Context context, int times) {
        String fullId = tinyDB.getString(ADMOB_FULL_SCREEN_ID);
        if (!fullId.equals("")) {
            AdMobManager.getInstance().fullscreenAdmobShow(context, fullId, times);
        }
    }

    public void setupBannerAdmob(Context context, BannerAdmobView bannerAdmobView) {
        String bannerId = tinyDB.getString(ADMOB_BANNER_ID);
        if (!bannerId.equals("")) {
            AdMobManager.getInstance().bannerAdmobSetup(context, bannerAdmobView, bannerId);
        }
    }

    public void setupNativeAdmob(NativeExpressAdView nativeExpressAdView, AdSize adSize) {
        String nativeId = tinyDB.getString(ADMOB_NATIVE_ID);
        if (!nativeId.equals("")) {
            AdMobManager.getInstance().nativeAdmobSetup(nativeExpressAdView, adSize, nativeId);
        }
    }

    public void openMarket(Context context) {
        if (isShowAdsMore) {
            Log.d("datdb", "openMarket");
            Intent intent = new Intent(context, MoreAppActivity.class);
            context.startActivity(intent);
        }
    }

    public void showAdsFullScreen(Activity context, int times) {
        if (MethodUtils.isNetworkConnected(context)) {
            Log.d("datdb", "show full: " + appFulls.size());
            timesFull = tinyDB.getInt(TIMES_SHOW_FULL, 0);
            boolean show = timesFull % (times + 1) == 0;
            if (appFulls.size() > 0 && (times == 0 || show)) {
                context.startActivity(new Intent(context, FullScreenAds.class));
            }
            tinyDB.putInt(TIMES_SHOW_FULL, timesFull + 1);
        }
    }

//    public void showAdsNative(Activity context, int times) {
//        if (MethodUtils.isNetworkConnected(context)) {
//            timesNative = tinyDB.getInt(TIMES_SHOW_NATIVE, 0);
//            boolean show = timesNative % (times + 1) == 0;
//            if (appInfos.size() > 0 && (times == 0 || show)) {
//                Intent intent = new Intent(context, NativeAdsActivity.class);
//                intent.putExtra(NativeAdsActivity.FULL_SCREEN, isFullScreen(context));
//                intent.putExtra(NativeAdsActivity.FLAG, context.getWindow().getDecorView().getSystemUiVisibility());
//                context.startActivity(intent);
//            }
//            tinyDB.putInt(TIMES_SHOW_NATIVE, timesNative + 1);
//        }
//    }


    public void showAdsNativeDialog(Activity context, int times) {
        if (MethodUtils.isNetworkConnected(context)) {
            timesNative = tinyDB.getInt(TIMES_SHOW_NATIVE, 0);
            boolean show = timesNative % (times + 1) == 0;
            if (appNatives.size() > 0 && (times == 0 || show)) {
                NativeAds nativeAds = new NativeAds(context);
                nativeAds.show();
            }
            tinyDB.putInt(TIMES_SHOW_NATIVE, timesNative + 1);
        }
    }

    public boolean parseAdsModel(String json, boolean save) {
        if (save) {
            if (!tinyDB.getString(DATA_SAVE).equals(json)) {
                tinyDB.putString(DATA_SAVE, json);
            } else if (appInfos.size() != 0) {
                return false;
            }
        }

        try {
            appInfos.clear();
            JSONObject object = new JSONObject(json);
            JSONArray jsonArray = object.getJSONArray("Items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                AppInfo appInfo = new AppInfo();
                appInfo.setId_ads(jsonObject.getInt("id_ads"));
                appInfo.setPackage_name(jsonObject.getString("package_name"));
                appInfo.setTitle(jsonObject.getString("title"));
                appInfo.setShort_des(jsonObject.getString("short_des"));
                appInfo.setIcon(jsonObject.getString("icon"));
                appInfo.setCover(jsonObject.getString("cover"));
                appInfo.setIs_more_apps(jsonObject.getInt("is_more_apps"));
                appInfo.setIs_back_apps(jsonObject.getInt("is_back_apps"));
                appInfo.setIs_full(jsonObject.getInt("is_full"));
                appInfo.setIs_popup(jsonObject.getInt("is_popup"));
                appInfo.setIs_banner(jsonObject.getInt("is_banner"));

                JSONObject ss = jsonObject.getJSONObject("screenshot");
                ScreenShot screenShot = new ScreenShot();
                screenShot.setSs1(ss.getString("ss1"));
                screenShot.setSs2(ss.getString("ss2"));
                screenShot.setSs3(ss.getString("ss3"));
                appInfo.setScreenShot(screenShot);

                appInfos.add(appInfo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        getAppFulls();
        getAppNatives();
        getAppBanners();

        return true;

    }

    public void parseDone() {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ACTION_ADS_LOADED));
    }


    public AppInfo getAppMain() {
        if (appMain == null && appInfos.size() > 0) {
            appMain = appInfos.get(0);
        }
        return appMain;
    }

    public boolean isShowAdsMore() {
        return isShowAdsMore;
    }

    public ArrayList<AppInfo> getAppRecommended() {

        if (appRecommended.size() > 0) {
            return appRecommended;
        }

        if (appInfos.size() > 7) {
            for (int i = 1; i < 7; i++) {
                appRecommended.add(appInfos.get(i));
            }
        } else {
            appRecommended.addAll(appInfos);
        }

        return appRecommended;
    }

    public ArrayList<AppInfo> getAppMayLike() {
        if (appMayLike.size() > 0 || appInfos.size() <= 7) {
            return appMayLike;
        }

        for (int i = 7; i < appInfos.size(); i++) {
            appMayLike.add(appInfos.get(i));
        }

        return appMayLike;
    }

    public ArrayList<AppInfo> getAppFulls() {
        if (appFulls.size() == 0 && appInfos.size() > 0) {
            for (int i = 0; i < appInfos.size(); i++) {
                if (appInfos.get(i).getIs_full() == 1) {
                    appFulls.add(appInfos.get(i));
                }
            }
        }
        return appFulls;
    }

    public ArrayList<AppInfo> getAppNatives() {
        if (appNatives.size() == 0 && appInfos.size() > 0) {
            for (int i = 0; i < appInfos.size(); i++) {
                if (appInfos.get(i).getIs_popup() == 1) {
                    appNatives.add(appInfos.get(i));
                }
            }
        }
        return appNatives;
    }

    public ArrayList<AppInfo> getAppBanners() {
        if (appBanners.size() == 0 && appInfos.size() > 0) {
            for (int i = 0; i < appInfos.size(); i++) {
                if (appInfos.get(i).getIs_banner() == 1) {
                    appBanners.add(appInfos.get(i));
                }
            }
        }
        return appBanners;
    }

    public ArrayList<AppInfo> getAppOnBackpress() {
        if (appOnBackpress.size() == 0 && appInfos.size() > 0) {
            for (int i = 0; i < appInfos.size(); i++) {
                if (appInfos.get(i).getIs_back_apps() == 1) {
                    appOnBackpress.add(appInfos.get(i));
                }
            }
        }
        return appOnBackpress;
    }

    public ArrayList<AppInfo> getAppInfos() {
        return appInfos;
    }

    public boolean isShowAdsOnBackpress() {
        return isShowAdsOnBackpress;
    }


    public void openAppInGooglePlay(Context context, String packageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public void openDeveloperInGooglePlay(Context context, String developer) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=" + developer)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + developer)));
        }
    }

    public void savePushToServer(boolean b) {
        tinyDB.putBoolean(PUSH_TO_SERVER, b);
    }

    private boolean isPushToServer() {
        return tinyDB.getBoolean(PUSH_TO_SERVER, false);
    }

    private Comparator<AppInfo> sortAppInfo = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo o1, AppInfo o2) {
            if (o1.getId_ads() > o2.getId_ads()) {
                return 1;
            }
            return 0;
        }
    };

}
