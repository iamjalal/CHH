package com.jalals.test.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jalals.test.activity.MainActivity;
import com.jalals.test.app.AppController;
import com.jalals.test.app.R;
import com.jalals.test.model.Twitter;
import com.jalals.test.twitter.TokenRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private ImageView mProgressLogo;
    private TextView mAuthMessage;

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

        mProgressLogo = (ImageView)view.findViewById(R.id.progress_logo);
        mAuthMessage = (TextView)view.findViewById(R.id.auth_message);

        return view;
    }

    @Override
    public void onClick(View v) {
        requestAccessToken();
    }

    private void requestAccessToken() {

        mAuthMessage.setText(getResources().getText(R.string.login_progress));
        startProgressAnimation();

        StringRequest request = new TokenRequest(Request.Method.POST, Twitter.URLs.TOKEN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String token = jsonObject.optString(Twitter.JSON.ACCESS_TOKEN);

                            if(token == null) {
                                return;
                            }

                            saveToken(token);
                            stopProgressAnimation();
                            mAuthMessage.setText(getResources().getText(R.string.login_progress));

                            ((MainActivity)getActivity()).showTweetsFragment();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stopProgressAnimation();
                        error.printStackTrace();
                    }
                }
        );

        AppController.getInstance().addToRequestQueue(request);
    }

    private void saveToken(String token) {
        SharedPreferences prefs = getActivity().getSharedPreferences(
                AppController.APP_PREFERENCES, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppController.PREFS_ACCESS_TOKEN, token);
        editor.commit();
    }

    private void startProgressAnimation() {
        RotateAnimation anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1800);

        mProgressLogo.startAnimation(anim);
    }

    private void stopProgressAnimation() {
        mProgressLogo.setAnimation(null);
    }
}
