package com.ai.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReplyMessage {
    @JsonProperty(value = "id")
    private Long tweetId;
    @JsonProperty(value = "conversation_id")
    private Long conversationId;
    @JsonProperty(value = "datetime")
    private String dateTime;
    @JsonProperty(value = "user_id")
    private Long userId;
    private String username;
    @JsonProperty(value = "tweet")
    private String content;
    @JsonProperty(value = "replies_count")
    private Integer replyCount;
    @JsonProperty(value = "likes_count")
    private Integer likeCount;
    private Boolean retweet;
}