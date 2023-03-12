package com.ai.demo.domain;

public enum TweetType {
    MAIN_CONVERSATION,
    REPLY_BY_OWNER, // means: this tweet is a reply by owner and should be included while fetching the main conversation
    SIMPLE_REPLY // means: is a reply by Twitter users
}
