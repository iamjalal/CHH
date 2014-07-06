package com.jalals.test.ui_data;


import org.json.JSONObject;

public class Tweet {

    private class JSON_ATTR {
        private static final String ID = "id";
        private static final String USER = "user";
        private static final String TEXT = "text";
    }

    private final String mId;
    private final User mUser;
    private final String mText;

    public Tweet(JSONObject jsonObject) {
        mId = jsonObject.optString(JSON_ATTR.ID);
        mUser = new User(jsonObject.optJSONObject(JSON_ATTR.USER));
        mText = jsonObject.optString(JSON_ATTR.TEXT);
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
}
