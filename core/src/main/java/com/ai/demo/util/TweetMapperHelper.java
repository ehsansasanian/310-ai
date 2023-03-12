package com.ai.demo.util;

import com.ai.demo.domain.Tweet;

import java.util.LinkedHashMap;

import static com.ai.demo.domain.TweetType.MAIN_CONVERSATION;
import static com.ai.demo.domain.TweetType.REPLY_BY_OWNER;

public final class TweetMapperHelper {
    public static Tweet mapTweet(LinkedHashMap<String, Object> each) {
        // map tweet
        Tweet tweet = new Tweet((String) each.get("date"), (String) each.get("username"), (String) each.get("content"),
                (Integer) each.get("like_count"), (Integer) each.get("retweet_count"), (Integer) each.get("reply_count"),
                (Long) each.get("in_reply_to_tweet_id"), (Long) each.get("conversation_id"), (Long) each.get("tweet_id"),
                (Integer) each.get("view_count"));
        // check if the fetched tweet is in reply to someone
        return (tweet.getInReplyToTweetId() != null) ? tweet.setTweetType(REPLY_BY_OWNER) : tweet.setTweetType(MAIN_CONVERSATION);
    }
}
