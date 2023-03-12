package com.ai.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThreadConversation {
    @JsonProperty(value = "main_tweet")
    private TweetDTO mainConversation;
    private List<TweetDTO> replies;
    @JsonProperty(value = "number_of_comments")
    private Integer numberOfComments;
}
