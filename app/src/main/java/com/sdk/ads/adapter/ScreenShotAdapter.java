package com.sdk.ads.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sdk.ads.R;


public class ScreenShotAdapter extends RecyclerView.Adapter<ScreenShotAdapter.ViewHolder> {

    private Context context;
    private String[] ss;
    private OnScreenShotClick onScreenShotClick;

    public ScreenShotAdapter(Context context, String[] ss, OnScreenShotClick onScreenShotClick) {
        this.context = context;
        this.ss = ss;
        this.onScreenShotClick = onScreenShotClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_screenshot, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Glide.with(context).load(ss[i]).into(viewHolder.ss);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onScreenShotClick != null) {
                    onScreenShotClick.onClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ss.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ss = itemView.findViewById(R.id.ss);
        }
    }

    public interface OnScreenShotClick {
        void onClick();
    }
}
