package com.sdk.ads.ads;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdk.ads.R;
import com.sdk.ads.adapter.ScreenShotAdapter;
import com.sdk.ads.manager.AdsManager;
import com.sdk.ads.model.AppInfo;

import java.util.ArrayList;
import java.util.Random;

public class FullScreenAds extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private ImageView iconApp;
    private TextView titleApp;
    private TextView shortDesApp;
    private TextView closeAds;
    private RecyclerView rcvSs;
    private ScreenShotAdapter screenShotAdapter;

    private AppInfo appInfo;
    private boolean isClose;

    private void findViews() {
        constraintLayout = findViewById(R.id.click);
        closeAds = findViewById(R.id.closeAds);
        iconApp = findViewById(R.id.iconApp);
        titleApp = findViewById(R.id.titleApp);
        shortDesApp = findViewById(R.id.shortDesApp);
        rcvSs = findViewById(R.id.rcvSs);
        rcvSs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        closeAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClose) {
                    finish();
                }
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appInfo != null) {
                    AdsManager.getInstance().openAppInGooglePlay(FullScreenAds.this, appInfo.getPackage_name());
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreen();

        setContentView(R.layout.activity_full_screen_ads);
        findViews();
        ArrayList<AppInfo> fulls = AdsManager.getInstance().getAppFulls();
        Random random = new Random();
        int r = random.nextInt(fulls.size());
        appInfo = fulls.get(r);

        screenShotAdapter = new ScreenShotAdapter(FullScreenAds.this, new String[]{appInfo.getScreenShot().getSs1(), appInfo.getScreenShot().getSs2(), appInfo.getScreenShot().getSs3()}, onScreenShotClick);
        rcvSs.setAdapter(screenShotAdapter);

        Glide.with(FullScreenAds.this).load(appInfo.getIcon()).into(iconApp);
        titleApp.setText(appInfo.getTitle());
        shortDesApp.setText(appInfo.getShort_des());

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                closeAds.setText((millisUntilFinished / 1000) + "");
            }

            @Override
            public void onFinish() {
                closeAds.setText("X");
                isClose = true;
            }
        }.start();
    }

    private ScreenShotAdapter.OnScreenShotClick onScreenShotClick = new ScreenShotAdapter.OnScreenShotClick() {
        @Override
        public void onClick() {
            if (appInfo != null) {
                AdsManager.getInstance().openAppInGooglePlay(FullScreenAds.this, appInfo.getPackage_name());
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        setFullScreen();
    }

    private void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(flags);
    }

    @Override
    public void onBackPressed() {
        if (isClose) {
            finish();
        }
    }
}
