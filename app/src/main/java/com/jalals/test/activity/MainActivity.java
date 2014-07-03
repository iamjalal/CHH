package com.jalals.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jalals.test.app.AppController;
import com.jalals.test.app.R;

import org.json.JSONObject;

public class MainActivity extends Activity {

    private static final String URL = "http://api.androidhive.info/volley/person_object.json";

    private TextView mMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessage = (TextView)findViewById(R.id.message);
        requestJsonObject();
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

    private void requestJsonObject() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mMessage.setText(response.toString(4));
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            mMessage.setText(error.toString());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
