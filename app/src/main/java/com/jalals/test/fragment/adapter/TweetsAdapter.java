package com.jalals.test.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.jalals.test.app.AppController;
import com.jalals.test.app.R;
import com.jalals.test.ui_data.Tweet;

import java.util.ArrayList;
import java.util.List;


public class TweetsAdapter extends BaseAdapter {

    private List<Tweet> mTweets = new ArrayList<Tweet>();
    private Context mContext;

    public TweetsAdapter(Context context) {
        mContext = context;
    }

    public void setEntries(List<Tweet> tweets) {
        mTweets = tweets;
        notifyDataSetChanged();
    }

    public List<Tweet> getEntries() {
        return mTweets;
    }

    public void add(Tweet tweet) {
        mTweets.add(tweet);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTweets.size();
    }

    @Override
    public Tweet getItem(int position) {
        return mTweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tweet_item, null);
            holder = new ViewHolder();
            holder.userImage = (NetworkImageView) convertView.findViewById(R.id.user_image);
            holder.userName = (TextView) convertView.findViewById(R.id.user_name);
            holder.tweetText = (TextView) convertView.findViewById(R.id.tweet_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Tweet tweet = getItem(position);

        holder.userName.setText(tweet.getUser().getName());
        holder.userImage.setImageUrl(tweet.getUser().getImageUrl(),
                AppController.getInstance().getImageLoader());
        holder.tweetText.setText(tweet.getText());

        return convertView;
    }

    private static class ViewHolder {
        private NetworkImageView userImage;
        private TextView userName;
        private TextView tweetText;
    }
}