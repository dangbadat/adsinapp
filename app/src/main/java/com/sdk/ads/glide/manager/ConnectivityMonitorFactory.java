package com.sdk.ads.glide.manager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sdk.ads.glide.manager.ConnectivityMonitor;

/**
 * A factory class that produces a functional
 * {@link com.sdk.ads.glide.manager.ConnectivityMonitor}.
 */
public interface ConnectivityMonitorFactory {

  @NonNull
  com.sdk.ads.glide.manager.ConnectivityMonitor build(
          @NonNull Context context,
          @NonNull ConnectivityMonitor.ConnectivityListener listener);
}
