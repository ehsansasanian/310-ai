package com.ai.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tweets"
//        , indexes = {
//        @Index(name = "idx.tweet_id", columnList = "tweet_id"),
//        @Index(name = "idx.username", columnList = "username")
//        @Index(name = "idx.tweet_id_username", columnList = "tweet_id, username")
//}
)
@Data
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(length = 4001)
    private String content;
    private String username;
    @Column(name = "retweet_count")
    private Integer retweetCount;

    @Column(name = "native_tweet_id", unique = true)
    private Long tweetId;
    @Column(name = "in_reply_to_tweet_id")
    private Long inReplyToTweetId;
    @Column(name = "conversation_id")
    private Long conversationId;
    @Column(name = "like_count")
    private Integer likeCount;
    @Column(name = "tweet_date")
    private LocalDateTime tweetDate;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "reply_count")
    private Integer replyCount;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "tweet_type")
    @Enumerated(EnumType.STRING)
    private TweetType tweetType;

    public Tweet(String date, String username, String content, Integer likeCount, Integer retweetCount,
                 Integer replyCount, Long inReplyToTweetId, Long conversationId, Long tweetId, Integer viewCount) {
        this.content = content;
        this.username = username;
        this.retweetCount = retweetCount;
        this.inReplyToTweetId = inReplyToTweetId;
        this.conversationId = conversationId;
        this.likeCount = likeCount;
        this.tweetDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
        this.replyCount = replyCount;
        this.tweetId = tweetId;
        this.viewCount = viewCount;
        this.creationDate = LocalDateTime.now();
    }

    public Tweet() {
    }

    private LocalDateTime dateMapper(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
    }

    public Tweet setTweetType(TweetType tweetType) {
        this.tweetType = tweetType;
        return this;
    }
}
