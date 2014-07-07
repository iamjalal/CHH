package com.jalals.test.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jalals.test.activity.MainActivity;
import com.jalals.test.app.AppController;
import com.jalals.test.app.R;

import com.jalals.test.fragment.adapter.TweetsAdapter;
import com.jalals.test.model.Twitter;
import com.jalals.test.twitter.TweetsRequest;
import com.jalals.test.ui_data.Tweet;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TweetsFragment extends Fragment {

    TweetsAdapter adapter;

    public static TweetsFragment newInstance() {
        TweetsFragment fragment = new TweetsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets, container, false);

        adapter = new TweetsAdapter(getActivity());
        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(adapter);

        ListView tweetList = (ListView) view.findViewById(R.id.tweet_list);
        animationAdapter.setAbsListView(tweetList);
        tweetList.setAdapter(animationAdapter);

        loadTweets(Twitter.Values.SCREEN_NAME);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tweets_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_logout) {
            performLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadTweets(String query) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(Twitter.JSON.SCREEN_NAME, query));

        JsonArrayRequest request = new TweetsRequest(Twitter.URLs.TWEETS_URL,
            new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    parseTweets(response);
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
        }, params, ((MainActivity)getActivity()).getAccessToken());

        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseTweets(JSONArray response) throws JSONException {

        int count = response != null ? response.length() : 0;
        for(int i = 0; i < count; i++) {
            Tweet tweet = new Tweet(response.getJSONObject(i));
            adapter.add(tweet);
        }
    }

    private void performLogout() {
        MainActivity act = (MainActivity)getActivity();
        if(act != null) {
            act.showLoginFragment();
            act.resetAccessToken();
        }
    }
}
