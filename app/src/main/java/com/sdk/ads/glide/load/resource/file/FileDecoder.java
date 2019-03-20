package com.sdk.ads.glide.load.resource.file;

import android.support.annotation.NonNull;
import com.sdk.ads.glide.load.Options;
import com.sdk.ads.glide.load.ResourceDecoder;
import com.sdk.ads.glide.load.engine.Resource;
import com.sdk.ads.glide.load.resource.file.FileResource;

import java.io.File;

/**
 * A simple {@link ResourceDecoder} that creates resource for a given {@link
 * File}.
 */
public class FileDecoder implements ResourceDecoder<File, File> {

  @Override
  public boolean handles(@NonNull File source, @NonNull Options options) {
    return true;
  }

  @Override
  public Resource<File> decode(@NonNull File source, int width, int height,
      @NonNull Options options) {
    return new FileResource(source);
  }
}
