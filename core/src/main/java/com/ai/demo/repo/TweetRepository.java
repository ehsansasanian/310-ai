package com.ai.demo.repo;

import com.ai.demo.domain.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Tweet findByTweetId(Long tweetId);

    List<Tweet> findByConversationId(Long conversationId);
}
