package com.sdk.ads.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdk.ads.glide.Glide;
import com.sdk.ads.R;
import com.sdk.ads.manager.AdsManager;
import com.sdk.ads.model.AppInfo;

import java.util.ArrayList;
import java.util.Random;

public class NativeAds extends Dialog {

    private Context context;
    private AppInfo appInfo;
    private boolean isClose;

    private ConstraintLayout click;
    private ImageView iconApp;
    private TextView titleApp;
    private TextView shortDesApp;
    private ImageView coverApp;
    private TextView closeAds;

    public NativeAds(Activity context) {
        super(context);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    private void init(Activity context) {
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (isFullScreen(context)) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        this.getWindow().getDecorView().setSystemUiVisibility(context.getWindow().getDecorView().getSystemUiVisibility());

        setContentView(R.layout.dialog_native_ads);
        findViews();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setData();
    }

    private void findViews() {
        click = findViewById(R.id.click);
        iconApp = findViewById(R.id.iconApp);
        titleApp = findViewById(R.id.titleApp);
        shortDesApp = findViewById(R.id.shortDesApp);
        coverApp = findViewById(R.id.coverApp);
        closeAds = findViewById(R.id.closeAds);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appInfo != null) {
                    AdsManager.getInstance().openAppInGooglePlay(context, appInfo.getPackage_name());
                }
            }
        });

        closeAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClose) {
                    dismiss();
                }
            }
        });
    }

    private void setData() {
        Random random = new Random();
        ArrayList<AppInfo> natives = AdsManager.getInstance().getAppNatives();
        int r = random.nextInt(natives.size());
        appInfo = natives.get(r);
        if (appInfo != null) {
            isClose = false;
            Glide.with(context).load(appInfo.getIcon()).into(iconApp);
            Glide.with(context).load(appInfo.getCover()).into(coverApp);
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
                    setCancelable(true);
                    setCanceledOnTouchOutside(true);
                }
            }.start();
        }
    }

    private boolean isFullScreen(Activity context) {
        int flg = context.getWindow().getAttributes().flags;
        boolean flag = false;
        if ((flg & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            flag = true;
        }
        return flag;
    }
}
