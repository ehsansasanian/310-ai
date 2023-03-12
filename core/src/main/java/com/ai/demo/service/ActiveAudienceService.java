package com.ai.demo.service;

import com.ai.demo.domain.Account;
import com.ai.demo.domain.ActiveAudience;
import com.ai.demo.domain.Tweet;
import com.ai.demo.repo.ActiveAudienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveAudienceService {
    private final ActiveAudienceRepository activeAudienceRepository;

    public void logActiveAudience(Tweet tweet, Account account) {
        ActiveAudience activeAudience = activeAudienceRepository.findByUsernameAndAudience(tweet.getUsername(), account)
                .orElseGet(() -> getNewActiveAudience(tweet.getUsername(), account));
        activeAudience.incrementActivityCounter();
        activeAudienceRepository.save(activeAudience);
    }

    private ActiveAudience getNewActiveAudience(String username, Account account) {
        ActiveAudience activeAudience = new ActiveAudience();
        activeAudience.setAudienceUserName(username);
        activeAudience.setAccount(account);
        return activeAudience;
    }
}