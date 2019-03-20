package com.sdk.ads.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.sdk.ads.glide.manager.RequestManagerRetriever;
import com.sdk.ads.glide.module.AppGlideModule;
import java.util.Set;

/**
 * Allows {@link AppGlideModule}s to exclude {@link com.sdk.ads.glide.annotation.GlideModule}s to
 * ease the migration from {@link com.sdk.ads.glide.annotation.GlideModule}s to Glide's annotation
 * processing system and optionally provides a
 * {@link RequestManagerRetriever.RequestManagerFactory} impl.
 */
abstract class GeneratedAppGlideModule extends AppGlideModule {
  /**
   * This method can be removed when manifest parsing is no longer supported.
   */
  @NonNull
  abstract Set<Class<?>> getExcludedModuleClasses();

  @Nullable
  RequestManagerRetriever.RequestManagerFactory getRequestManagerFactory() {
    return null;
  }
}
