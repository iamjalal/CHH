package com.jalals.test.twitter;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetsRequest extends JsonArrayRequest {

    private String mAccessToken;

    private static final String UTF_ENCODING = "UTF-8";
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_BEARER = "Bearer ";

    public TweetsRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener,
                         List<NameValuePair> params, String accessToken) {
        super(url + "?" + URLEncodedUtils.format(params, UTF_ENCODING), listener, errorListener);
        mAccessToken = accessToken;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String auth = AUTH_BEARER + mAccessToken;
        headers.put(AUTH_HEADER, auth);
        return headers;
    }
}