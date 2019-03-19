package com.sdk.ads.adapter;

import android.content.Context;
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

public class MayLikeAdapter extends RecyclerView.Adapter<MayLikeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AppInfo> appMaylikes;
    private OnAdClickListener onAdClickListener;

    public MayLikeAdapter(Context context, ArrayList<AppInfo> appInfos, OnAdClickListener onAdClickListener) {
        this.context = context;
        this.appMaylikes = appInfos;
        this.onAdClickListener = onAdClickListener;
    }

    public void setAppInfos(ArrayList<AppInfo> appInfos) {
        this.appMaylikes = appInfos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MayLikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_maylike, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MayLikeAdapter.ViewHolder viewHolder, int i) {
        final AppInfo appInfo = appMaylikes.get(i);
        Glide.with(context).load(appInfo.getIcon()).into(viewHolder.iconApp);
        viewHolder.titleApp.setText(appInfo.getTitle());
        viewHolder.shortDesApp.setText(appInfo.getShort_des());

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
        return appMaylikes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iconApp;
        TextView titleApp;
        TextView shortDesApp;

        private void findViews(View itemView) {
            iconApp = (ImageView) itemView.findViewById(R.id.iconApp);
            titleApp = (TextView) itemView.findViewById(R.id.titleApp);
            shortDesApp = (TextView) itemView.findViewById(R.id.shortDesApp);
        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }
    }
}
