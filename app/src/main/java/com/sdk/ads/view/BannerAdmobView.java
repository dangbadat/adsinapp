package com.sdk.ads.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

public class BannerAdmobView extends LinearLayout {
    public BannerAdmobView(Context context) {
        super(context);
        init();
    }

    public BannerAdmobView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerAdmobView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
    }
}
