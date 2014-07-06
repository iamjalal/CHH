package com.jalals.test.twitter;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jalals.test.model.Twitter;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetsRequest extends JsonArrayRequest {

    private String mAccessToken;

    public TweetsRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener,
                         List<NameValuePair> params, String accessToken) {
        super(url + "?" + URLEncodedUtils.format(params, "UTF-8"), listener, errorListener);
        mAccessToken = accessToken;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String auth = "Bearer " + mAccessToken;
        headers.put("Authorization", auth);
        return headers;
    }
}