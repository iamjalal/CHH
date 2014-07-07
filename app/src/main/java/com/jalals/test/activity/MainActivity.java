package com.jalals.test.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jalals.test.app.AppController;
import com.jalals.test.app.R;
import com.jalals.test.fragment.LoginFragment;
import com.jalals.test.fragment.TweetsFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getAccessToken() == null) {
            showLoginFragment();
        }
        else {
            showTweetsFragment();
        }
    }

    public void showLoginFragment() {
        LoginFragment fragment = LoginFragment.newInstance();
        getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out).replace(R.id.fragment_container, fragment).commit();
    }

    public void showTweetsFragment() {
        TweetsFragment fragment = TweetsFragment.newInstance();
        getFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out).replace(R.id.fragment_container, fragment).commit();
    }

    public String getAccessToken() {
        SharedPreferences prefs = getSharedPreferences(AppController.APP_PREFERENCES, 0);
        return prefs.getString(AppController.PREFS_ACCESS_TOKEN, null);
    }

    public void resetAccessToken() {
        SharedPreferences prefs = getSharedPreferences(AppController.APP_PREFERENCES, 0);
        prefs.edit().putString(AppController.PREFS_ACCESS_TOKEN, null)
            .commit();
    }
}
