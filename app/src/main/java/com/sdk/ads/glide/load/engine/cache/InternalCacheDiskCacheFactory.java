package com.sdk.ads.glide.load.engine.cache;

import android.content.Context;

import com.sdk.ads.glide.load.engine.cache.DiskCache;
import com.sdk.ads.glide.load.engine.cache.DiskLruCacheFactory;

import java.io.File;

/**
 * Creates an {@link com.sdk.ads.glide.disklrucache.DiskLruCache} based disk cache in the internal
 * disk cache directory.
 */
// Public API.
@SuppressWarnings({"WeakerAccess", "unused"})
public final class InternalCacheDiskCacheFactory extends DiskLruCacheFactory {

  public InternalCacheDiskCacheFactory(Context context) {
    this(context, com.sdk.ads.glide.load.engine.cache.DiskCache.Factory.DEFAULT_DISK_CACHE_DIR,
        com.sdk.ads.glide.load.engine.cache.DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
  }

  public InternalCacheDiskCacheFactory(Context context, long diskCacheSize) {
    this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
  }

  public InternalCacheDiskCacheFactory(final Context context, final String diskCacheName,
                                       long diskCacheSize) {
    super(new CacheDirectoryGetter() {
      @Override
      public File getCacheDirectory() {
        File cacheDirectory = context.getCacheDir();
        if (cacheDirectory == null) {
          return null;
        }
        if (diskCacheName != null) {
          return new File(cacheDirectory, diskCacheName);
        }
        return cacheDirectory;
      }
    }, diskCacheSize);
  }
}
