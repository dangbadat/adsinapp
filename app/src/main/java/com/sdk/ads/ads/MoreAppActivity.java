package com.sdk.ads.ads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdk.ads.glide.Glide;
import com.sdk.ads.R;
import com.sdk.ads.adapter.MayLikeAdapter;
import com.sdk.ads.adapter.RecommendAdapter;
import com.sdk.ads.callback.OnAdClickListener;
import com.sdk.ads.manager.AdsManager;
import com.sdk.ads.model.AppInfo;

import java.util.ArrayList;

public class MoreAppActivity extends AppCompatActivity {

    private ImageView backPress;

    private RelativeLayout rltTop;
    private ImageView coverMain;
    private ImageView developer;
    private ImageView iconMain;
    private TextView titleMain;
    private TextView shortDesMain;
    private RecyclerView rcvRecommended;
    private RecyclerView rcvYouMayLike;

    private RecommendAdapter recommendAdapter;
    private MayLikeAdapter mayLikeAdapter;

    private AppInfo appMain;
    private ArrayList<AppInfo> appRecommended;
    private ArrayList<AppInfo> appMayLike;

    private TextView tvRecommend, tvMaylike;

    private void findViews() {
        tvRecommend = findViewById(R.id.tvRecommended);
        tvMaylike = findViewById(R.id.tvMaylike);
        backPress = findViewById(R.id.backPress);
        developer = findViewById(R.id.developer);
        rltTop = findViewById(R.id.rltTop);
        coverMain = findViewById(R.id.coverMain);
        iconMain = findViewById(R.id.iconMain);
        titleMain = findViewById(R.id.titleMain);
        shortDesMain = findViewById(R.id.shortDesMain);
        rcvRecommended = findViewById(R.id.rcvRecommended);
        rcvRecommended.setLayoutManager(new GridLayoutManager(this, 3));
        recommendAdapter = new RecommendAdapter(this, appRecommended, onAdClickListener);
        rcvRecommended.setAdapter(recommendAdapter);

        rcvYouMayLike = findViewById(R.id.rcvYouMayLike);
        rcvYouMayLike.setLayoutManager(new LinearLayoutManager(this));
        mayLikeAdapter = new MayLikeAdapter(this, appMayLike, onAdClickListener);
        rcvYouMayLike.setAdapter(mayLikeAdapter);

        rcvRecommended.setNestedScrollingEnabled(false);
        rcvYouMayLike.setNestedScrollingEnabled(false);

        rltTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appMain != null) {
                    AdsManager.getInstance().openAppInGooglePlay(MoreAppActivity.this, appMain.getPackage_name());
                }
            }
        });

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AdsManager.getInstance().openDeveloperInGooglePlay(MoreAppActivity.this);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoActionBar);
        setContentView(R.layout.activity_more_app);

        appRecommended = new ArrayList<>();
        appMayLike = new ArrayList<>();
        findViews();
        appMain = AdsManager.getInstance().getAppMain();
        appRecommended = AdsManager.getInstance().getAppRecommended();
        appMayLike = AdsManager.getInstance().getAppMayLike();

        recommendAdapter.setAppInfos(appRecommended);
        mayLikeAdapter.setAppInfos(appMayLike);

        if (appMain != null) {
            Glide.with(this).load(appMain.getCover()).into(coverMain);
            Glide.with(this).load(appMain.getIcon()).into(iconMain);
            titleMain.setText(appMain.getTitle());
            shortDesMain.setText(appMain.getShort_des());
        }

        if (appRecommended.size() > 0) {
            tvRecommend.setVisibility(View.VISIBLE);
        } else {
            tvRecommend.setVisibility(View.GONE);
        }

        if (appMayLike.size() > 0) {
            tvMaylike.setVisibility(View.VISIBLE);
        } else {
            tvMaylike.setVisibility(View.GONE);
        }
    }

    private OnAdClickListener onAdClickListener = new OnAdClickListener() {
        @Override
        public void onClick(String packageName) {
            AdsManager.getInstance().openAppInGooglePlay(MoreAppActivity.this, packageName);
        }
    };
}
