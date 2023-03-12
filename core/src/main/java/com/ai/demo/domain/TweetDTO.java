package com.ai.demo.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TweetDTO {
    private LocalDateTime date;
    private String username;
    private String content;
    private Integer likes;
}