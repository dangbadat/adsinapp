package com.sdk.ads.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdk.ads.glide.Glide;
import com.sdk.ads.R;
import com.sdk.ads.manager.AdsManager;
import com.sdk.ads.model.AppInfo;
import com.sdk.ads.utils.MethodUtils;

import java.util.ArrayList;
import java.util.Random;

public class BannerAdsView extends ConstraintLayout {

    private static final int time = 20000;

    private Context context;

    private ConstraintLayout click;
    private ImageView iconApp;
    private TextView titleApp;
    private TextView shortDesApp;

    private AppInfo appInfo;

    private ArrayList<AppInfo> appInfos;
    private Random random;
    private Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setData();
        }
    };

    private void findViews() {
        click = findViewById(R.id.click);
        iconApp = findViewById(R.id.iconApp);
        titleApp = findViewById(R.id.titleApp);
        shortDesApp = findViewById(R.id.shortDesApp);

        click.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appInfo != null) {
                    AdsManager.getInstance().openAppInGooglePlay(context, appInfo.getPackage_name());
                }
            }
        });
    }

    public BannerAdsView(Context context) {
        super(context);
        init(context);
    }

    public BannerAdsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BannerAdsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_banner_ads, this, true);
        appInfos = new ArrayList<>();
        handler = new Handler();
        random = new Random();
        findViews();
        setData();
        LocalBroadcastManager.getInstance(context).registerReceiver(adsLoaded, new IntentFilter(AdsManager.ACTION_ADS_LOADED));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(runnable);
        LocalBroadcastManager.getInstance(context).unregisterReceiver(adsLoaded);
    }

    private BroadcastReceiver adsLoaded = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d("datdb", "onReceive: banner");
            appInfos = AdsManager.getInstance().getAppBanners();
            setData();
        }
    };

    private void setData() {
        if (!MethodUtils.isNetworkConnected(context) || appInfos.size() == 0) {
//            Log.d("datdb", "ko dc");
            setVisibility(GONE);
            handler.removeCallbacks(runnable);
        } else {
            setVisibility(VISIBLE);
            appInfo = appInfos.get(random.nextInt(appInfos.size()));
//            Log.d("datdb", "appInfo: " + appInfo.toString());
            Glide.with(context).load(appInfo.getIcon()).into(iconApp);
            titleApp.setText(appInfo.getTitle());
            shortDesApp.setText(appInfo.getShort_des());
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, time);
        }
    }


}
