package com.sdk.ads.glide.load.engine;

import com.sdk.ads.glide.load.Key;
import com.sdk.ads.glide.load.Options;
import com.sdk.ads.glide.load.Transformation;
import com.sdk.ads.glide.load.engine.EngineKey;

import java.util.Map;

class EngineKeyFactory {

  @SuppressWarnings("rawtypes")
  com.sdk.ads.glide.load.engine.EngineKey buildKey(Object model, Key signature, int width, int height,
                                                    Map<Class<?>, Transformation<?>> transformations, Class<?> resourceClass,
                                                    Class<?> transcodeClass, Options options) {
    return new com.sdk.ads.glide.load.engine.EngineKey(model, signature, width, height, transformations, resourceClass,
        transcodeClass, options);
  }
}
