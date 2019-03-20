package com.sdk.ads.glide.load.engine.bitmap_recycle;

import com.sdk.ads.glide.load.engine.bitmap_recycle.Poolable;
import com.sdk.ads.glide.util.Util;
import java.util.Queue;

abstract class BaseKeyPool<T extends com.sdk.ads.glide.load.engine.bitmap_recycle.Poolable> {
  private static final int MAX_SIZE = 20;
  private final Queue<T> keyPool = Util.createQueue(MAX_SIZE);

  T get() {
    T result = keyPool.poll();
    if (result == null) {
      result = create();
    }
    return result;
  }

  public void offer(T key) {
    if (keyPool.size() < MAX_SIZE) {
      keyPool.offer(key);
    }
  }

  abstract T create();
}
