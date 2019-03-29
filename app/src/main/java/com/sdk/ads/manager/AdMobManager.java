package com.sdk.ads.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

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

    private static InterstitialAd interstitialAd;

    public static void init(Context ctx, String appId) {
        context = ctx;
        MobileAds.initialize(context, appId);
        tinyDB = new TinyDB(context);
        requestAdsFullScreen();
    }

    public static AdMobManager getInstance() {
        if (instance == null) {
            instance = new AdMobManager();
        }
        return instance;
    }

    private static void requestAdsFullScreen() {
        String fullId = tinyDB.getString(AdsManager.ADMOB_FULL_SCREEN_ID);
        String fullIDInApp = "";

        try {
            fullIDInApp = context.getResources().getString(R.string.abmob_full_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String id = "";
        if (!fullId.equals("")) {
            id = fullId;
        } else if (!fullIDInApp.equals("")) {
            id = fullIDInApp;
        }

        if (!id.equals("")) {
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId(id);
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
            interstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdClosed() {
                    if (dialogLoadAds != null && dialogLoadAds.isShowing()) {
                        dialogLoadAds.dismiss();
                        dialogLoadAds = null;
                    }
                    super.onAdClosed();
                }
            });
        }
    }

    public void fullscreenAdmobShow(Context context, boolean isShowDialog, String messDialog, final String key, int times) {
        final int count = tinyDB.getInt(TIMES_SHOW_FULL_ADMOB + "_" + key, 0);
        boolean show = count % (times + 1) == 0;
        if ((times == 0 || show) && interstitialAd != null && interstitialAd.isLoaded()) {

            if (isShowDialog) {
                dialogLoadAds = new ProgressDialog(context);
                dialogLoadAds.setCancelable(false);
                dialogLoadAds.setCanceledOnTouchOutside(false);
                dialogLoadAds.setMessage(messDialog);
                dialogLoadAds.show();

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showAds();
                    }
                }, 1000);
            } else {
                showAds();
            }
        } else if (interstitialAd != null && !interstitialAd.isLoaded()) {
            requestAdsFullScreen();
        } else if (interstitialAd == null) {
            requestAdsFullScreen();
        }
        tinyDB.putInt(TIMES_SHOW_FULL_ADMOB + "_" + key, count + 1);
    }

    private void showAds() {
        interstitialAd.show();
        requestAdsFullScreen();
    }

    public void bannerAdmobSetup(final Context context, final BannerAdmobView bannerAdmobView,
                                 final String unitId) {
        bannerAdmobView.post(new Runnable() {
            @Override
            public void run() {
                AdView adView = new AdView(context);
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId(unitId);
                bannerAdmobView.addView(adView);

                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdLeftApplication() {
                        super.onAdLeftApplication();
                    }
                });
            }
        });
    }

    public void nativeAdmobSetup(NativeExpressAdView adView, AdSize adSize, String unitId) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdSize(adSize);
        adView.setAdUnitId(unitId);
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }
        });
    }
}
