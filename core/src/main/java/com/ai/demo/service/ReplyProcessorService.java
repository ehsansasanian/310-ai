package com.ai.demo.service;

import com.ai.demo.domain.Account;
import com.ai.demo.domain.ReplyMessage;
import com.ai.demo.domain.Tweet;
import com.ai.demo.domain.TweetType;
import com.ai.demo.repo.TweetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyProcessorService {
    private final TweetRepository tweetRepository;
    private final ActiveAudienceService activeAudienceService;

    @Transactional
    public void processReply(ReplyMessage replyMessage, Account trackingAccount) {
        if (trackingAccount == null) {
            log.error("Tracking account is null. message was not proceed for conversation {}.", replyMessage.getConversationId());
            return;
        }
        Tweet mainConversation = tweetRepository.findByTweetId(replyMessage.getConversationId());
        if (mainConversation == null) {
            log.error("[Main Conversation Not Found For Tweet {}]", replyMessage.getTweetId());
            return;
        }

        // if the reply belongs to the account we're tracking
        if (Objects.equals(replyMessage.getUsername(), trackingAccount.getUsername())) {
            // it should already exist in the DB
            Tweet existingTweet = tweetRepository.findByTweetId(replyMessage.getTweetId());
            // only update its type
            existingTweet.setTweetType(TweetType.REPLY_BY_OWNER);
            tweetRepository.save(existingTweet);
        } else {
            Tweet reply = this.getReply(replyMessage, mainConversation);
            reply.setConversationOwner(trackingAccount);
            tweetRepository.save(reply);
            activeAudienceService.logActiveAudience(reply, trackingAccount);
        }
        log.info("Reply saved successfully.");
    }

    private Tweet getReply(ReplyMessage replyMessage, Tweet mainConversation) {
        Tweet reply = new Tweet();
        reply.setContent(replyMessage.getContent());
        reply.setUsername(replyMessage.getUsername());
        reply.setRetweetCount(replyMessage.getRetweetCounts());
        reply.setTweetId(replyMessage.getTweetId());
        reply.setInReplyToTweetId(mainConversation.getTweetId());
        reply.setConversationId(replyMessage.getConversationId());
        reply.setLikeCount(replyMessage.getLikeCount());
        reply.setCreationDate(LocalDateTime.now());
        reply.setTweetDate(LocalDateTime.parse(replyMessage.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")));
        reply.setReplyCount(mainConversation.getReplyCount());
        reply.setTweetType(TweetType.SIMPLE_REPLY);
        return reply;
    }
}