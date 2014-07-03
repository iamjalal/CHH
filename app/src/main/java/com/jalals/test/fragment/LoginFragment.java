package com.jalals.test.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jalals.test.app.AppController;
import com.jalals.test.app.R;
import com.jalals.test.model.Twitter;
import com.jalals.test.twitter.TokenRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment implements View.OnClickListener {

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = (Button)view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.v("TEST", "onClick!!!!");
        requestAccessToken();
    }

    private void requestAccessToken() {

        StringRequest jsonObjReq = new TokenRequest(Request.Method.POST, Twitter.URLs.TOKEN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String token = jsonObject.optString(Twitter.JSON.ACCESS_TOKEN);
                            Log.v("TEST", "Access token: "+token);

                            if(token == null) {
                                return;
                            }

                            saveToken(token);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void saveToken(String token) {
        SharedPreferences prefs = getActivity().getSharedPreferences(AppController.APP_PREFERENCES, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppController.PREFS_ACCESS_TOKEN, token);
        editor.commit();
    }
}
