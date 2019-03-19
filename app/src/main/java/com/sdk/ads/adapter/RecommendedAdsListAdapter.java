package com.sdk.ads.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdk.ads.R;
import com.sdk.ads.callback.OnAdClickListener;
import com.sdk.ads.model.AppInfo;

import java.util.ArrayList;

public class RecommendedAdsListAdapter extends RecyclerView.Adapter<RecommendedAdsListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AppInfo> appInfos;
    private OnAdClickListener onAdClickListener;
    private int colorText = Color.BLACK;

    public RecommendedAdsListAdapter(Context context, ArrayList<AppInfo> appInfos, OnAdClickListener onAdClickListener) {
        this.context = context;
        this.appInfos = appInfos;
        this.onAdClickListener = onAdClickListener;
    }

    public void setAppInfos(ArrayList<AppInfo> appInfos) {
        this.appInfos = appInfos;
        notifyDataSetChanged();
    }

    public void setColorText(int color) {
        this.colorText = color;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_recommended_ads_list, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final AppInfo appInfo = appInfos.get(i);
        Glide.with(context).load(appInfo.getIcon()).fitCenter().into(viewHolder.iconApp);
        viewHolder.titleApp.setText(appInfo.getTitle());
        viewHolder.titleApp.setTextColor(colorText);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAdClickListener != null) {
                    onAdClickListener.onClick(appInfo.getPackage_name());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleApp;
        ImageView iconApp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleApp = itemView.findViewById(R.id.titleApp);
            iconApp = itemView.findViewById(R.id.iconApp);
        }
    }
}
