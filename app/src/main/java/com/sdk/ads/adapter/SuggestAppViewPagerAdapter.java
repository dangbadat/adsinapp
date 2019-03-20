package com.sdk.ads.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdk.ads.glide.Glide;
import com.sdk.ads.R;
import com.sdk.ads.manager.AdsManager;
import com.sdk.ads.model.AppInfo;

import java.util.ArrayList;

public class SuggestAppViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<AppInfo> appInfos;

    public SuggestAppViewPagerAdapter(Context context, ArrayList<AppInfo> appInfos) {
        this.context = context;
        this.appInfos = appInfos;
    }

    public void setAppInfos(ArrayList<AppInfo> appInfos) {
        this.appInfos = appInfos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_ad_viewpager, container, false);

        final AppInfo appInfo = appInfos.get(position);

        ImageView iconApp = rootView.findViewById(R.id.iconApp);
        TextView titleApp = rootView.findViewById(R.id.titleApp);
        TextView shortDesApp = rootView.findViewById(R.id.shortDesApp);
        ImageView cover = rootView.findViewById(R.id.cover);

        Glide.with(context).load(appInfo.getIcon()).into(iconApp);
        Glide.with(context).load(appInfo.getCover()).into(cover);
        titleApp.setText(appInfo.getTitle());
        shortDesApp.setText(appInfo.getShort_des());

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsManager.getInstance().openAppInGooglePlay(context, appInfo.getPackage_name());
            }
        });

        container.addView(rootView);
        return rootView;
    }

    @Override
    public int getCount() {
        return appInfos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
