package com.sdk.ads.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import java.util.List;

public class MethodUtils {
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getNavigationBarHeight(Context context) {
        boolean isNavigationBar = context.getResources().getBoolean(context.getResources().getIdentifier("config_showNavigationBar", "bool", "android"));
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && isNavigationBar) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isAppInstall(PackageManager manager, List<ApplicationInfo> applicationInfos, String packageName) {
        for (ApplicationInfo app : applicationInfos) {
            if (manager.getLaunchIntentForPackage(app.packageName) != null) {
                if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0 || (app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {

                } else {
                    if (app.packageName.equals(packageName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
