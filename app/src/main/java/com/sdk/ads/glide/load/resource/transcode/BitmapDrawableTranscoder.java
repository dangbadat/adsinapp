package com.sdk.ads.glide.load.resource.transcode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.sdk.ads.glide.load.Options;
import com.sdk.ads.glide.load.engine.Resource;
import com.sdk.ads.glide.load.engine.bitmap_recycle.BitmapPool;
import com.sdk.ads.glide.load.resource.bitmap.LazyBitmapDrawableResource;
import com.sdk.ads.glide.load.resource.transcode.ResourceTranscoder;
import com.sdk.ads.glide.util.Preconditions;

/**
 * An {@link com.sdk.ads.glide.load.resource.transcode.ResourceTranscoder} that converts {@link
 * Bitmap}s into {@link BitmapDrawable}s.
 */
public class BitmapDrawableTranscoder implements ResourceTranscoder<Bitmap, BitmapDrawable> {
  private final Resources resources;

  // Public API.
  @SuppressWarnings("unused")
  public BitmapDrawableTranscoder(@NonNull Context context) {
    this(context.getResources());
  }

  /**
   * @deprecated Use {@link #BitmapDrawableTranscoder(Resources)}, {@code bitmapPool} is unused.
   */
  @Deprecated
  public BitmapDrawableTranscoder(
      @NonNull Resources resources, @SuppressWarnings("unused") BitmapPool bitmapPool) {
    this(resources);
  }

  public BitmapDrawableTranscoder(@NonNull Resources resources) {
    this.resources = Preconditions.checkNotNull(resources);
  }

  @Nullable
  @Override
  public Resource<BitmapDrawable> transcode(@NonNull Resource<Bitmap> toTranscode,
      @NonNull Options options) {
    return LazyBitmapDrawableResource.obtain(resources, toTranscode);
  }
}
