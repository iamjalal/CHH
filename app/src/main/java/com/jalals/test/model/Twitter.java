package com.jalals.test.model;

/**
 * Created by jalals on 7/3/2014.
 */
public class Twitter {

    public class URLs {
        public static final String TOKEN_URL = "https://api.twitter.com/oauth2/token";
        public static final String TWEETS_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json";
        public static final String PROFILE_IMAGE = "https://api.twitter.com/1/users/profile_image?screen_name="+Values.SCREEN_NAME+"&size=bigger";
    }

    public class Values {
        public static final String API_KEY = "pxqeT3njnY72Bg88LBjsQiC7h";
        public static final String API_SECRET = "E2XZdA2pClBpbuF2PQ7ReH7o3PIRQQNHk5K1gmsH6qGw1RdzmI";
        public static final String SCREEN_NAME = "CHH_BCN";
    }

    public class JSON {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String SCREEN_NAME = "screen_name";
    }
}
