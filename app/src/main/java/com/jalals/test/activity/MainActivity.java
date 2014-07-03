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

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getAccessToken() == null) {
            showLoginFragment();
        }
        else {
            Log.v("TEST", "Got a token. Fetch tweets!!!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoginFragment() {

        LoginFragment fragment = (LoginFragment) LoginFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    private String getAccessToken() {
        SharedPreferences prefs = getSharedPreferences(AppController.APP_PREFERENCES, 0);
        return prefs.getString(AppController.PREFS_ACCESS_TOKEN, null);
    }
}
