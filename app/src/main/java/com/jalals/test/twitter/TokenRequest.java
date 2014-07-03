package com.jalals.test.twitter;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.jalals.test.model.Twitter;

import java.util.HashMap;
import java.util.Map;

public class TokenRequest extends StringRequest {

    public TokenRequest(int method, String url, Response.Listener<String> listener,
                 Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credentials");
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String auth = "Basic " + Base64.encodeToString((Twitter.Values.API_KEY
                        + ":" + Twitter.Values.API_SECRET).getBytes(),
                Base64.NO_WRAP
        );

        headers.put("Authorization", auth);
        return headers;
    }
}
