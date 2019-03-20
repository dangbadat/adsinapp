package com.sdk.ads.glide.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.sdk.ads.glide.manager.ConnectivityMonitor;
import com.sdk.ads.glide.manager.ConnectivityMonitorFactory;
import com.sdk.ads.glide.manager.DefaultConnectivityMonitor;
import com.sdk.ads.glide.manager.NullConnectivityMonitor;

/**
 * A factory class that produces a functional {@link com.sdk.ads.glide.manager.ConnectivityMonitor}
 * if the application has the {@code android.permission.ACCESS_NETWORK_STATE} permission and a no-op
 * non functional {@link com.sdk.ads.glide.manager.ConnectivityMonitor} if the app does not have
 * the required permission.
 */
public class DefaultConnectivityMonitorFactory implements ConnectivityMonitorFactory {
  private static final String TAG = "ConnectivityMonitor";
  private static final String NETWORK_PERMISSION = "android.permission.ACCESS_NETWORK_STATE";

  @NonNull
  @Override
  public com.sdk.ads.glide.manager.ConnectivityMonitor build(
      @NonNull Context context,
      @NonNull ConnectivityMonitor.ConnectivityListener listener) {
    int permissionResult = ContextCompat.checkSelfPermission(context, NETWORK_PERMISSION);
    boolean hasPermission = permissionResult == PackageManager.PERMISSION_GRANTED;
    if (Log.isLoggable(TAG, Log.DEBUG)) {
      Log.d(
          TAG,
          hasPermission
              ? "ACCESS_NETWORK_STATE permission granted, registering connectivity monitor"
              : "ACCESS_NETWORK_STATE permission missing, cannot register connectivity monitor");
    }
    return hasPermission
        ? new com.sdk.ads.glide.manager.DefaultConnectivityMonitor(context, listener) : new com.sdk.ads.glide.manager.NullConnectivityMonitor();
  }
}
