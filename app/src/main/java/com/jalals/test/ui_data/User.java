package com.jalals.test.ui_data;


import com.jalals.test.model.Twitter;

import org.json.JSONObject;

public class User {

    public class JSON_ATTR {
        private static final String ID = "id";
        private static final String NAME = "name";
    }

    private final String mId;
    private final String mName;

    public User(JSONObject jsonObject) {
        mId = jsonObject.optString(JSON_ATTR.ID);
        mName = jsonObject.optString(JSON_ATTR.NAME);
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return Twitter.URLs.PROFILE_IMAGE;
    }
}
