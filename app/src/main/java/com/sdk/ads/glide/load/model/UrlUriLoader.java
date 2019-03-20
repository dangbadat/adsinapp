package com.sdk.ads.glide.load.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.sdk.ads.glide.load.Options;
import com.sdk.ads.glide.load.model.GlideUrl;
import com.sdk.ads.glide.load.model.ModelLoader;
import com.sdk.ads.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles http/https Uris by delegating to the {@link com.sdk.ads.glide.load.model.ModelLoader} for {@link
 * com.sdk.ads.glide.load.model.GlideUrl GlideUrls}.
 *
 * @param <Data> The type of data this Loader will obtain for a {@link Uri}.
 */
public class UrlUriLoader<Data> implements com.sdk.ads.glide.load.model.ModelLoader<Uri, Data> {
  private static final Set<String> SCHEMES = Collections.unmodifiableSet(
      new HashSet<>(
          Arrays.asList(
              "http",
              "https"
          )
      )
  );
  private final com.sdk.ads.glide.load.model.ModelLoader<com.sdk.ads.glide.load.model.GlideUrl, Data> urlLoader;

  // Public API.
  @SuppressWarnings("WeakerAccess")
  public UrlUriLoader(com.sdk.ads.glide.load.model.ModelLoader<com.sdk.ads.glide.load.model.GlideUrl, Data> urlLoader) {
    this.urlLoader = urlLoader;
  }

  @Override
  public LoadData<Data> buildLoadData(@NonNull Uri uri, int width, int height,
      @NonNull Options options) {
    com.sdk.ads.glide.load.model.GlideUrl glideUrl = new com.sdk.ads.glide.load.model.GlideUrl(uri.toString());
    return urlLoader.buildLoadData(glideUrl, width, height, options);
  }

  @Override
  public boolean handles(@NonNull Uri uri) {
    return SCHEMES.contains(uri.getScheme());
  }

  /**
   * Loads {@link InputStream InputStreams} from {@link Uri Uris} with http
   * or https schemes.
   */
  public static class StreamFactory implements ModelLoaderFactory<Uri, InputStream> {

    @NonNull
    @Override
    public ModelLoader<Uri, InputStream> build(MultiModelLoaderFactory multiFactory) {
      return new UrlUriLoader<>(multiFactory.build(GlideUrl.class, InputStream.class));
    }

    @Override
    public void teardown() {
      // Do nothing.
    }
  }
}
