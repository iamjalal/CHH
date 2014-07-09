package com.jalals.test.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
import com.jalals.test.util.EndlessScrollListener;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class TweetsFragment extends Fragment implements  OnRefreshListener,
        EndlessScrollListener.OnEndReachedListener, AppController.OnConnectionErrorListener {

    private static final String TWEET_LIST = "tweetList";
    private static final long LOAD_LATEST = -1;

    private PullToRefreshLayout mRootLayout;
    private ListView mTweetList;
    private TweetsAdapter mAdapter;

    private EndlessScrollListener mScrollListener;

    private List<Tweet> mTweets = new ArrayList<Tweet>();

    public static TweetsFragment newInstance() {
        TweetsFragment fragment = new TweetsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        AppController.getInstance().setConnectionErrorListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState != null) {
            mTweets = savedInstanceState.getParcelableArrayList(TWEET_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets, container, false);

        mRootLayout = (PullToRefreshLayout) view.findViewById(R.id.root_layout);
        ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable()
                .listener(this).setup(mRootLayout);

        mTweetList = (ListView) view.findViewById(R.id.tweet_list);
        mScrollListener = new EndlessScrollListener();
        mScrollListener.setOnEndReachedListener(this);
        mTweetList.setOnScrollListener(mScrollListener);

        setListAdapter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mTweets.isEmpty()) {
            loadTweets(LOAD_LATEST);
        }
        else {
            updateUi();
        }
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

    private void loadTweets(long maxId) {

        mRootLayout.setRefreshing(true);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(Twitter.JSON.SCREEN_NAME, Twitter.Values.SCREEN_NAME));

        if(maxId != LOAD_LATEST) {
            params.add(new BasicNameValuePair(Twitter.JSON.MAX_ID, String.valueOf(maxId)));
        }

        JsonArrayRequest request = new TweetsRequest(Twitter.URLs.TWEETS_URL,
            new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(mRootLayout.isRefreshing()) {
                        mRootLayout.setRefreshComplete();
                    }
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
                onConnectionError();
                error.printStackTrace();
            }
        }, params, ((MainActivity)getActivity()).getAccessToken());

        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseTweets(JSONArray response) throws JSONException {

        int count = response != null ? response.length() : 0;
        for(int i = 0; i < count; i++) {
            Tweet tweet = new Tweet(response.getJSONObject(i));
            mAdapter.add(tweet);
        }
    }

    private void performLogout() {
        MainActivity act = (MainActivity)getActivity();
        if(act != null) {
            act.showLoginFragment();
            act.resetAccessToken();
        }
    }

    private void updateUi() {

        if(mAdapter == null) {
            setListAdapter();
        }

        mAdapter.setEntries(mTweets);
    }

    private void setListAdapter() {
        mAdapter = new TweetsAdapter(getActivity());
        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(mTweetList);
        mTweetList.setAdapter(animationAdapter);
    }

    @Override
    public void onRefreshStarted(View view) {
        setListAdapter();
        mScrollListener.reset();
        loadTweets(LOAD_LATEST);
    }

    @Override
    public void onEndReached() {
        if(mAdapter != null && mAdapter.getCount() > 0) {
            long maxId = mAdapter.getItem(mAdapter.getCount() - 1).getId() - 1;
            loadTweets(maxId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(TWEET_LIST,
                mAdapter != null ? new ArrayList<Tweet>(mAdapter.getEntries()) : new ArrayList<Tweet>());
    }

    @Override
    public void onConnectionError() {
        Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_LONG).show();
        if(mRootLayout.isRefreshing()) {
            mRootLayout.setRefreshComplete();
        }
    }
}
