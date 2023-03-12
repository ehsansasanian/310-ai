package com.ai.demo.service;

import com.ai.demo.domain.ThreadConversation;
import com.ai.demo.domain.Tweet;
import com.ai.demo.domain.TweetDTO;
import com.ai.demo.repo.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;

    public ThreadConversation getThread(Long conversationId) {
        Tweet mainTweet = tweetRepository.findByTweetId(conversationId);

        if (mainTweet == null) {
            return null;
        }

        ThreadConversation threadConversation = new ThreadConversation();
        threadConversation.setMainConversation(mapTweetDTO(mainTweet));
        List<TweetDTO> replies = tweetRepository.findByConversationId(conversationId).stream()
                .filter(t -> !Objects.equals(t.getId(), mainTweet.getId()))
                .map(this::mapTweetDTO)
                .toList();
        threadConversation.setNumberOfComments(replies.size());
        threadConversation.setReplies(replies);
        return threadConversation;
    }

    private TweetDTO mapTweetDTO(Tweet tweet) {
        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setContent(tweet.getContent());
        tweetDTO.setDate(tweet.getTweetDate());
        tweetDTO.setUsername(tweet.getUsername());
        tweetDTO.setLikes(tweet.getLikeCount());
        return tweetDTO;
    }
}
