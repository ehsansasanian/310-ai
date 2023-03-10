package com.ai.demo.scraper;

import com.ai.demo.domain.Tweet;

import java.util.LinkedHashMap;

public final class TweetMapperHelper {
    public static Tweet mapTweet(LinkedHashMap<String, Object> each) {
        return new Tweet((String) each.get("date"), (String) each.get("username"), (String) each.get("content"),
                (Integer) each.get("like_count"), (Integer) each.get("retweet_count"), (Integer) each.get("reply_count"),
                (Long) each.get("in_reply_to_tweet_id"), (Long) each.get("conversation_id"), (Long) each.get("tweet_id"),
                (Integer) each.get("view_count"));
    }
}
