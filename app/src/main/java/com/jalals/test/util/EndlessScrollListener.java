package com.jalals.test.util;

import android.widget.AbsListView;

/**
 * Created by Ben Cull
 * http://benjii.me/2010/08/endless-scrolling-listview-in-android/
 *
 * Edited by Jalal Souky
 * Minor changes were made as pagination support and visible threshold setter constructor
 * are not needed. Also a callback interface was defined to be implemented by the TweetsFragment.
 */
public class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int previousTotal = 0;
    private boolean loading = true;
    private OnEndReachedListener listener;

    public void setOnEndReachedListener(OnEndReachedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (loading && totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            if (listener != null) {
                listener.onEndReached();
            }
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void reset() {
        previousTotal = 0;
    }

    public interface OnEndReachedListener {
        public void onEndReached();
    }
}