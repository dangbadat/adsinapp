package com.sdk.ads.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdk.ads.R;
import com.sdk.ads.adapter.SuggestAppViewPagerAdapter;
import com.sdk.ads.manager.AdsManager;
import com.sdk.ads.model.AppInfo;

import java.util.ArrayList;

public class DialogSuggest extends Dialog {

    private Context context;
    private TextView exit, cancel;
    private ViewPager viewPager;
    private SuggestAppViewPagerAdapter adapter;
    private ArrayList<AppInfo> appInfos;
    private RecyclerView rcvDot;
    private DotAdapter dotAdapter;

    public DialogSuggest(@NonNull Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public boolean isShow() {
        if (appInfos.size() == 0) {
            appInfos = AdsManager.getInstance().getAppOnBackpress();
            adapter.setAppInfos(appInfos);
            dotAdapter.setAppInfos(appInfos);
//            viewPager.setOffscreenPageLimit(appInfos.size());
        }
        if (appInfos.size() > 0 && AdsManager.getInstance().isShowAdsOnBackpress()) {
            viewPager.setCurrentItem(0);
            return true;
        }
        return false;
    }

    private void init(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_suggest);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        this.context = context;

        appInfos = AdsManager.getInstance().getAppOnBackpress();
        exit = findViewById(R.id.exit);
        cancel = findViewById(R.id.cancel);
        viewPager = findViewById(R.id.viewpager);
        exit.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        adapter = new SuggestAppViewPagerAdapter(context, appInfos);

        viewPager.setPageMargin(30);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        rcvDot = findViewById(R.id.rcvDot);
        rcvDot.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        dotAdapter = new DotAdapter(context, appInfos);
        rcvDot.setAdapter(dotAdapter);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            dotAdapter.setSelected(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == exit) {
                dismiss();
                ((Activity) context).finish();
            } else if (v == cancel) {
                dismiss();
            }
        }
    };

    private class DotAdapter extends RecyclerView.Adapter<DotAdapter.ViewHolder> {

        private Context context;
        private ArrayList<AppInfo> appInfos;
        private int selected = 0;

        public DotAdapter(Context context, ArrayList<AppInfo> appInfos) {
            this.context = context;
            this.appInfos = appInfos;
        }

        public void setAppInfos(ArrayList<AppInfo> appInfos) {
            this.appInfos = appInfos;
            notifyDataSetChanged();
        }

        public void setSelected(int selected) {
            this.selected = selected;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View rootView = LayoutInflater.from(context).inflate(R.layout.item_dot, viewGroup, false);
            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if (i == selected) {
                viewHolder.dot.setBackgroundResource(R.drawable.dot_selected);
            } else {
                viewHolder.dot.setBackgroundResource(R.drawable.dot_un_selected);
            }
        }

        @Override
        public int getItemCount() {
            return appInfos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            View dot;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                dot = itemView.findViewById(R.id.dot);
            }
        }
    }
}
