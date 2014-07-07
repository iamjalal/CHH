package com.jalals.test.ui_data;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Tweet implements Parcelable {

    private class JSON_ATTR {
        private static final String ID = "id";
        private static final String USER = "user";
        private static final String TEXT = "text";
    }

    private String mId;
    private User mUser;
    private String mText;

    public Tweet(JSONObject jsonObject) {
        mId = jsonObject.optString(JSON_ATTR.ID);
        mUser = new User(jsonObject.optJSONObject(JSON_ATTR.USER));
        mText = jsonObject.optString(JSON_ATTR.TEXT);
    }

    public Tweet(Parcel in) {
        mId = in.readString();
        mUser = in.readParcelable(User.class.getClassLoader());
        mText = in.readString();
    }

    public String getId() {
        return mId;
    }

    public User getUser() {
        return mUser;
    }

    public String getText() {
        return mText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeParcelable(mUser, flags);
        dest.writeString(mText);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
