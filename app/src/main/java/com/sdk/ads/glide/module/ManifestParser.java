package com.sdk.ads.glide.module;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.sdk.ads.glide.module.GlideModule;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses {@link com.sdk.ads.glide.module.GlideModule} references out of the AndroidManifest file.
 */
// Used only in javadoc.
@SuppressWarnings("deprecation")
@Deprecated
public final class ManifestParser {
  private static final String TAG = "ManifestParser";
  private static final String GLIDE_MODULE_VALUE = "GlideModule";

  private final Context context;

  public ManifestParser(Context context) {
    this.context = context;
  }

  @SuppressWarnings("deprecation")
  public List<com.sdk.ads.glide.module.GlideModule> parse() {
    if (Log.isLoggable(TAG, Log.DEBUG)) {
      Log.d(TAG, "Loading Glide modules");
    }
    List<com.sdk.ads.glide.module.GlideModule> modules = new ArrayList<>();
    try {
      ApplicationInfo appInfo = context.getPackageManager()
          .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
      if (appInfo.metaData == null) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
          Log.d(TAG, "Got null app info metadata");
        }
        return modules;
      }
      if (Log.isLoggable(TAG, Log.VERBOSE)) {
        Log.v(TAG, "Got app info metadata: " + appInfo.metaData);
      }
      for (String key : appInfo.metaData.keySet()) {
        if (GLIDE_MODULE_VALUE.equals(appInfo.metaData.get(key))) {
          modules.add(parseModule(key));
          if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Loaded Glide module: " + key);
          }
        }
      }
    } catch (PackageManager.NameNotFoundException e) {
      throw new RuntimeException("Unable to find metadata to parse GlideModules", e);
    }
    if (Log.isLoggable(TAG, Log.DEBUG)) {
      Log.d(TAG, "Finished loading Glide modules");
    }

    return modules;
  }

  @SuppressWarnings("deprecation")
  private static com.sdk.ads.glide.module.GlideModule parseModule(String className) {
    Class<?> clazz;
    try {
      clazz = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("Unable to find GlideModule implementation", e);
    }

    Object module = null;
    try {
      module = clazz.getDeclaredConstructor().newInstance();
    // These can't be combined until API minimum is 19.
    } catch (InstantiationException e) {
      throwInstantiateGlideModuleException(clazz, e);
    } catch (IllegalAccessException e) {
      throwInstantiateGlideModuleException(clazz, e);
    } catch (NoSuchMethodException e) {
      throwInstantiateGlideModuleException(clazz, e);
    } catch (InvocationTargetException e) {
      throwInstantiateGlideModuleException(clazz, e);
    }

    if (!(module instanceof com.sdk.ads.glide.module.GlideModule)) {
      throw new RuntimeException("Expected instanceof GlideModule, but found: " + module);
    }
    return (GlideModule) module;
  }

  private static void throwInstantiateGlideModuleException(Class<?> clazz, Exception e) {
    throw new RuntimeException("Unable to instantiate GlideModule implementation for " + clazz, e);
  }
}