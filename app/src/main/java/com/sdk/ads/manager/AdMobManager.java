package com.sdk.ads.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.sdk.ads.R;
import com.sdk.ads.utils.TinyDB;
import com.sdk.ads.view.BannerAdmobView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;

public class AdMobManager {

    private static AdMobManager instance;
    private static TinyDB tinyDB;
    private static final String TIMES_SHOW_FULL_ADMOB = "times_show_full_admob";
    private static Context context;
    private static ProgressDialog dialogLoadAds;
    private Handler handler;
    public static boolean isInit;

    private static InterstitialAd interstitialAd;

    public static void init(Context ctx, String appId) {
        isInit = true;
        context = ctx;
        MobileAds.initialize(context, appId);
        tinyDB = new TinyDB(context);
//        requestAdsFullScreen();
    }

    public static AdMobManager getInstance() {
        if (instance == null) {
            instance = new AdMobManager();
        }
        return instance;
    }

//    private static void requestAdsFullScreen() {
//        Log.d("datdb", "requestAdsFullScreen");
//        String fullId = tinyDB.getString(AdsManager.ADMOB_FULL_SCREEN_ID);
//        String fullIDInApp = "";
//
//        try {
//            fullIDInApp = context.getResources().getString(R.string.abmob_full_id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String id = "";
//        if (!fullId.equals("")) {
//            id = fullId;
//        } else if (!fullIDInApp.equals("")) {
//            id = fullIDInApp;
//        }
//
//        Log.d("datdb", "fullID: " + id);
//        if (!id.equals("")) {
//            interstitialAd = new InterstitialAd(context);
//            interstitialAd.setAdUnitId(id);
//            AdRequest adRequest = new AdRequest.Builder().build();
//            interstitialAd.loadAd(adRequest);
//        }
//    }

//    public void fullscreenAdmobShow(Context context, boolean isShowDialog, String messDialog, final String key, int times, final OnAdInterstitialAdListener listener) {
//        if (!isInit) {
//            if (listener != null) {
//                listener.onNotShowAd();
//            }
//            return;
//        }
//
//        int count = tinyDB.getInt(TIMES_SHOW_FULL_ADMOB + "_" + key, 0);
//        boolean show = count % (times + 1) == 0;
//        if (interstitialAd != null) {
//            if ((times == 0 || show) && count >= 1 && interstitialAd.isLoaded()) {
//                if (isShowDialog) {
//                    dialogLoadAds = new ProgressDialog(context);
//                    dialogLoadAds.setCancelable(false);
//                    dialogLoadAds.setCanceledOnTouchOutside(false);
//                    dialogLoadAds.setMessage(messDialog);
//                    dialogLoadAds.show();
//
//                    handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            showAds(listener);
//                        }
//                    }, 1000);
//                } else {
//                    showAds(listener);
//                }
//            } else if (!interstitialAd.isLoaded()) {
//                requestAdsFullScreen();
//                if (listener != null) {
//                    listener.onNotShowAd();
//                }
//            } else {
//                if (listener != null) {
//                    listener.onNotShowAd();
//                }
//            }
//        } else {
//            requestAdsFullScreen();
//            if (listener != null) {
//                listener.onNotShowAd();
//            }
//        }
//        tinyDB.putInt(TIMES_SHOW_FULL_ADMOB + "_" + key, count + 1);
//    }

//    private void showAds(final OnAdInterstitialAdListener listener) {
//        interstitialAd.show();
//
//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdOpened() {
//                Log.d("datdb", "onAdOpened");
//                if (listener != null) {
//                    listener.onAdOpen();
//                }
//                super.onAdOpened();
//            }
//
//            @Override
//            public void onAdClosed() {
//                Log.d("datdb", "onAdClosed");
//                if (dialogLoadAds != null && dialogLoadAds.isShowing()) {
//                    dialogLoadAds.dismiss();
//                    dialogLoadAds = null;
//                }
//                if (listener != null) {
//                    listener.onAdClose();
//                }
//                super.onAdClosed();
//            }
//
//            @Override
//            public void onAdLoaded() {
//                Log.d("datdb", "onAdLoaded");
//                super.onAdLoaded();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                Log.d("datdb", "onAdFailedToLoad: " + i);
//                if (dialogLoadAds != null && dialogLoadAds.isShowing()) {
//                    dialogLoadAds.dismiss();
//                    dialogLoadAds = null;
//                }
//                if (listener != null) {
//                    listener.onNotShowAd();
//                }
//                super.onAdFailedToLoad(i);
//            }
//        });
//
//        requestAdsFullScreen();
//    }

    public void fullscreenAdmobShow(Context context, final String key, int times, final OnAdInterstitialAdListener onAdInterstitialAdListener) {
        final int count = tinyDB.getInt(TIMES_SHOW_FULL_ADMOB + "_" + key, 0);
        boolean show = count % (times + 1) == 0;
        if ((times == 0 || show) && count >= 1) {
            final ProgressDialog dialogLoadAds = new ProgressDialog(context);
            dialogLoadAds.setCancelable(false);
            dialogLoadAds.setCanceledOnTouchOutside(false);
            dialogLoadAds.setMessage("Ad Loading...");
            dialogLoadAds.show();

            String fullId = tinyDB.getString(AdsManager.ADMOB_FULL_SCREEN_ID);
            String fullIDInApp = "";
            try {
                fullIDInApp = context.getResources().getString(R.string.abmob_full_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String unitID = "";
            if (!fullId.equals("")) {  // ưu tiên lấy id server trả về
                unitID = fullId;
            } else if (!fullIDInApp.equals("")) {
                unitID = fullIDInApp;
            }

            final InterstitialAd interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId(unitID);
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
            interstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdFailedToLoad(int i) {
                    dialogLoadAds.dismiss();
                    if (onAdInterstitialAdListener != null) {
                        onAdInterstitialAdListener.onFailedToLoad();
                    }
                }

                @Override
                public void onAdLoaded() {
                    dialogLoadAds.dismiss();
                    interstitialAd.show();
                    if (onAdInterstitialAdListener != null) {
                        onAdInterstitialAdListener.onOpen();
                    }
                }

                @Override
                public void onAdClosed() {
                    if (onAdInterstitialAdListener != null) {
                        onAdInterstitialAdListener.onClose();
                    }
                }
            });
        } else { // không hiển thị quảng cáo
            if (onAdInterstitialAdListener != null) {
                onAdInterstitialAdListener.onFailedToLoad();
            }
        }
        tinyDB.putInt(TIMES_SHOW_FULL_ADMOB + "_" + key, count + 1);
    }

    public void bannerAdmobSetup(final Context context, final BannerAdmobView bannerAdmobView, final String unitId) {
        Log.d("datdb", "bannerID: " + unitId);
        if (!isInit) {
            bannerAdmobView.setVisibility(View.GONE);
            return;
        }
        bannerAdmobView.post(new Runnable() {
            @Override
            public void run() {
                AdView adView = new AdView(context);
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId(unitId);
                bannerAdmobView.addView(adView);

                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
            }
        });
    }

    public void nativeAdmobSetup(NativeExpressAdView adView, AdSize adSize, String unitId) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdSize(adSize);
        adView.setAdUnitId(unitId);
        adView.loadAd(adRequest);
    }

    public interface OnAdInterstitialAdListener {
        void onOpen();

        void onClose();

        void onFailedToLoad();
    }

//    public interface OnAdInterstitialAdListener {
//        void onAdOpen();
//
//        void onAdClose();
//
//        void onNotShowAd();
//    }
}
