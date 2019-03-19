package com.sdk.ads.manager;

import android.content.Context;

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

    public static void init(Context context, String appId) {
        MobileAds.initialize(context, appId);
        tinyDB = new TinyDB(context);
    }

    public static AdMobManager getInstance() {
        if (instance == null) {
            instance = new AdMobManager();
        }
        return instance;
    }

    public void fullscreenAdmobShow(Context context, String unitId, int times) {
        int count = tinyDB.getInt(TIMES_SHOW_FULL_ADMOB, 0);
        boolean show = count % (times + 1) == 0;
        if (times == 0 || show) {
            final InterstitialAd interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId(unitId);
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    interstitialAd.show();
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
        tinyDB.putInt(TIMES_SHOW_FULL_ADMOB, count + 1);
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
