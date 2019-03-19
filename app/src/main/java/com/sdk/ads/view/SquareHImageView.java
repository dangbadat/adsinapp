package com.sdk.ads.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class SquareHImageView extends ImageView {
    public SquareHImageView(Context context) {
        super(context);
    }

    public SquareHImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareHImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int w = getResources().getDisplayMetrics().widthPixels / 7;
//        setMeasuredDimension(w, w);
        setMeasuredDimension(heightMeasureSpec, heightMeasureSpec);
    }
}
