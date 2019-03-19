package com.sdk.ads.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.sdk.ads.R;
import com.sdk.ads.manager.AdsManager;
import com.sdk.ads.utils.MethodUtils;

@SuppressLint("AppCompatCustomView")
public class BoxAdsView extends ImageView {

    private Context context;
    private boolean ready;

    public BoxAdsView(Context context) {
        super(context);
        init(context);
    }

    public BoxAdsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BoxAdsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setImageResource(R.drawable.trigger_box_ads);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.getDrawable();
        if (animationDrawable != null) {
            animationDrawable.setEnterFadeDuration(1000);
            animationDrawable.setExitFadeDuration(1000);
            animationDrawable.start();
        }
//        setShow(context, false);
        LocalBroadcastManager.getInstance(context).registerReceiver(adsLoaded, new IntentFilter(AdsManager.ACTION_ADS_LOADED));
    }

    private void setShow(Context context, boolean r) {
        if (MethodUtils.isNetworkConnected(context)) {
            ready = r;
        } else {
            ready = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AdsManager.getInstance().openMarket(context);
        }
        return true;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(adsLoaded);
    }

    private BroadcastReceiver adsLoaded = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            setShow(context, true);
        }
    };
}
