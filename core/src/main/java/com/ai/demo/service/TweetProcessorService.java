package com.ai.demo.service;

import com.ai.demo.domain.Account;
import com.ai.demo.domain.Tweet;
import com.ai.demo.domain.TweetDetail;
import com.ai.demo.repo.AccountRepository;
import com.ai.demo.repo.TweetDetailRepository;
import com.ai.demo.repo.TweetRepository;
import com.ai.demo.scraper.ScrapperGateway;
import com.ai.demo.scraper.TweetMapperHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TweetProcessorService {
    private final ScrapperGateway scrapperGateway;
    private final TweetRepository tweetRepository;
    private final AccountRepository accountRepository;
    private final TweetDetailRepository tweetDetailRepository;

    @Transactional
    @SuppressWarnings("unchecked")
    public void processTweets(Account account) {
        var threadName = Thread.currentThread().getName();
        var since = account.getLastProcessDate();
        LocalDateTime until = since.plusDays(1);

        if (until.isAfter(LocalDateTime.now())) {
            log.info("THREAD NAME = {}, Until, {}, all tweets are fetched for the username {}.", threadName,
                    since, account.getUsername());
            return;
        }

        log.info("THREAD NAME = {} process begins for the username {} since {} until {}. Calling Scrapper service to fetch the tweets.",
                threadName, account.getUsername(), since, until);
        var query = String.format("(from:%s) until:%s since:%s", account.getUsername(), until, since);
        List<LinkedHashMap<String, Object>> tweetsByQuery = (List<LinkedHashMap<String, Object>>) scrapperGateway.getTweetsByQuery(query);

        log.info("THREAD NAME = {},  {} tweets fetched. Mapping data process is beginning.", threadName, tweetsByQuery.size());
        List<TweetDetail> tweetDetails = new ArrayList<>();
        List<Tweet> tweets = tweetsByQuery.stream()
                .map(TweetMapperHelper::mapTweet)
                .filter(tweet -> tweet.getInReplyToTweetId() == null)
                .peek(tweet -> mapDetail(tweetDetails, tweet))
                .toList();

        tweetRepository.saveAll(tweets);
        tweetDetailRepository.saveAll(tweetDetails);
        account.setLastProcessDate(until);
        accountRepository.save(account);
        log.info("THREAD NAME = {}, {} tweets mapped, and saved in the DB.", threadName, tweets.size());
    }

    private static void mapDetail(List<TweetDetail> tweetDetails, Tweet tweet) {
        TweetDetail tweetDetail = new TweetDetail();
        tweetDetail.setTweet(tweet);
        tweetDetails.add(tweetDetail);
    }
}
