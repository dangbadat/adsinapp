package com.sdk.ads.glide.load.engine;

import com.sdk.ads.glide.load.Key;
import com.sdk.ads.glide.load.engine.EngineJob;
import com.sdk.ads.glide.load.engine.EngineResource;

interface EngineJobListener {

  void onEngineJobComplete(com.sdk.ads.glide.load.engine.EngineJob<?> engineJob, Key key, com.sdk.ads.glide.load.engine.EngineResource<?> resource);

  void onEngineJobCancelled(com.sdk.ads.glide.load.engine.EngineJob<?> engineJob, Key key);
}
