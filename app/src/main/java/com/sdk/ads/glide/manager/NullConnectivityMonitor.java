package com.sdk.ads.glide.manager;

import com.sdk.ads.glide.manager.ConnectivityMonitor;

/**
 * A no-op {@link com.sdk.ads.glide.manager.ConnectivityMonitor}.
 */
class NullConnectivityMonitor implements ConnectivityMonitor {

  @Override
  public void onStart() {
    // Do nothing.
  }

  @Override
  public void onStop() {
    // Do nothing.
  }

  @Override
  public void onDestroy() {
    // Do nothing.
  }
}
