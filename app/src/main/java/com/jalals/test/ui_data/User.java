package com.jalals.test.ui_data;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class User implements Parcelable {

    public class JSON_ATTR {
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String IMAGE_URL = "profile_image_url";
    }

    private long mId;
    private String mName;
    private String mImageUrl;

    public User(JSONObject jsonObject) {
        mId = jsonObject.optLong(JSON_ATTR.ID);
        mName = jsonObject.optString(JSON_ATTR.NAME);
        mImageUrl = jsonObject.optString(JSON_ATTR.IMAGE_URL);
    }

    public User(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mImageUrl = in.readString();
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return mImageUrl.replace("_normal", "");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mImageUrl);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
