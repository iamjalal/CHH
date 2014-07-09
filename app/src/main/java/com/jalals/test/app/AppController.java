package com.jalals.test.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    public static final String APP_PREFERENCES = "shared_preferences";
    public static final String PREFS_ACCESS_TOKEN = "access_token";

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private OnConnectionErrorListener mConnectionErrorListener;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req) {

        req.setTag(TAG);
        if(isConnected()) {
            getRequestQueue().add(req);
        }
        else {
            mConnectionErrorListener.onConnectionError();
        }
    }

    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public void setConnectionErrorListener(OnConnectionErrorListener listener) {
        mConnectionErrorListener = listener;
    }

    public interface OnConnectionErrorListener {
        public void onConnectionError();
    }
}