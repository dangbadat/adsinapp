package com.sdk.ads.glide.manager;

import android.support.annotation.NonNull;
import com.sdk.ads.glide.RequestManager;
import com.sdk.ads.glide.manager.RequestManagerTreeNode;

import java.util.Collections;
import java.util.Set;

/**
 * A {@link com.sdk.ads.glide.manager.RequestManagerTreeNode} that returns no relatives.
 */
final class EmptyRequestManagerTreeNode implements RequestManagerTreeNode {
    @NonNull
    @Override
    public Set<RequestManager> getDescendants() {
        return Collections.emptySet();
    }
}
