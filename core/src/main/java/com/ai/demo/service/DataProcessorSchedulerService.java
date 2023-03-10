package com.ai.demo.service;

import com.ai.demo.domain.Account;
import com.ai.demo.repo.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.concurrent.CompletableFuture.runAsync;

@Slf4j
@Service
@EnableScheduling
public class DataProcessorSchedulerService {
    private final AccountRepository accountRepository;
    private final ThreadPoolTaskExecutor tweetProcessorExecutor;
    private final TweetProcessorService tweetProcessorService;

    public DataProcessorSchedulerService(TweetProcessorService tweetProcessorService, AccountRepository accountRepository,
                                         @Qualifier("tweetProcessorExecutor") ThreadPoolTaskExecutor tweetProcessorExecutor) {
        this.tweetProcessorExecutor = tweetProcessorExecutor;
        this.accountRepository = accountRepository;
        this.tweetProcessorService = tweetProcessorService;
    }

    @Scheduled(fixedRate = 5_000)
    public void scheduledTweetProcessor() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            log.error("Tweet processor - Account Not Found!");
            return;
        }
        accounts.forEach(account -> runAsync(() -> tweetProcessorService.processTweets(account), tweetProcessorExecutor));
    }
}
