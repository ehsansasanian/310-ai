package com.ai.demo.service;

import com.ai.demo.domain.Account;
import com.ai.demo.domain.ActiveAudience;
import com.ai.demo.domain.Tweet;
import com.ai.demo.repo.AccountRepository;
import com.ai.demo.repo.ActiveAudienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveAudienceService {
    private final ActiveAudienceRepository activeAudienceRepository;
    private final AccountRepository accountRepository;

    public void logActiveAudience(Tweet tweet, Account account) {
        ActiveAudience activeAudience = activeAudienceRepository.findByUsernameAndAudience(tweet.getUsername(), account)
                .orElseGet(() -> getNewActiveAudience(tweet.getUsername(), account));
        activeAudience.incrementActivityCounter();
        activeAudienceRepository.save(activeAudience);
    }

    public List<ActiveAudience> getActiveAudience(String username) {
        return activeAudienceRepository.findByAccountOrderByReplyCountDesc(
                accountRepository.findByUsername(username));
    }

    private ActiveAudience getNewActiveAudience(String username, Account account) {
        ActiveAudience activeAudience = new ActiveAudience();
        activeAudience.setAudienceUserName(username);
        activeAudience.setAccount(account);
        return activeAudience;
    }
}