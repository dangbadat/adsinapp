package com.sdk.ads.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdk.ads.R;
import com.sdk.ads.adapter.RecommendedAdsListAdapter;
import com.sdk.ads.callback.OnAdClickListener;
import com.sdk.ads.manager.AdsManager;
import com.sdk.ads.model.AppInfo;
import com.sdk.ads.utils.MethodUtils;

import java.util.ArrayList;

public class ListAdsView extends LinearLayout {

    private TextView title;
    private RecyclerView rcvAds;
    private RecommendedAdsListAdapter adapter;
    private Context context;
    private ArrayList<AppInfo> appInfos;

    private int textColor;

    public ListAdsView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ListAdsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ListAdsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        setOrientation(VERTICAL);

        appInfos = AdsManager.getInstance().getAppInfos();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ListAdsView, defStyleAttr, 0);
        textColor = array.getColor(R.styleable.ListAdsView_textColor, Color.BLACK);
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_list_app_ad, this, true);
        title = findViewById(R.id.title);
        rcvAds = findViewById(R.id.rcvAds);
        rcvAds.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecommendedAdsListAdapter(context, appInfos, onAdClickListener);
        rcvAds.setAdapter(adapter);
        adapter.setColorText(textColor);
        title.setTextColor(textColor);
        checkList();
        LocalBroadcastManager.getInstance(context).registerReceiver(adsLoaded, new IntentFilter(AdsManager.ACTION_ADS_LOADED));
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(adsLoaded);
    }

    private BroadcastReceiver adsLoaded = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            appInfos = AdsManager.getInstance().getAppInfos();
            adapter.setAppInfos(appInfos);
            checkList();
        }
    };

    private void checkList() {
        if (!MethodUtils.isNetworkConnected(context) || appInfos.size() == 0) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    private OnAdClickListener onAdClickListener = new OnAdClickListener() {
        @Override
        public void onClick(String packageName) {
            AdsManager.getInstance().openAppInGooglePlay(context, packageName);
        }
    };

}
