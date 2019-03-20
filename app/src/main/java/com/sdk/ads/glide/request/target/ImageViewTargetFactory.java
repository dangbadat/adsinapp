package com.sdk.ads.glide.request.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.sdk.ads.glide.request.target.BitmapImageViewTarget;
import com.sdk.ads.glide.request.target.DrawableImageViewTarget;
import com.sdk.ads.glide.request.target.ViewTarget;

/**
 * A factory responsible for producing the correct type of
 * {@link com.sdk.ads.glide.request.target.Target} for a given {@link android.view.View} subclass.
 */
public class ImageViewTargetFactory {
  @NonNull
  @SuppressWarnings("unchecked")
  public <Z> com.sdk.ads.glide.request.target.ViewTarget<ImageView, Z> buildTarget(@NonNull ImageView view,
                                                                                    @NonNull Class<Z> clazz) {
    if (Bitmap.class.equals(clazz)) {
      return (com.sdk.ads.glide.request.target.ViewTarget<ImageView, Z>) new BitmapImageViewTarget(view);
    } else if (Drawable.class.isAssignableFrom(clazz)) {
      return (ViewTarget<ImageView, Z>) new DrawableImageViewTarget(view);
    } else {
      throw new IllegalArgumentException(
          "Unhandled class: " + clazz + ", try .as*(Class).transcode(ResourceTranscoder)");
    }
  }
}
