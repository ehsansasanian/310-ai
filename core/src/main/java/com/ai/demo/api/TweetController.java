package com.ai.demo.api;

import com.ai.demo.domain.ActiveAudience;
import com.ai.demo.domain.ThreadConversation;
import com.ai.demo.service.ActiveAudienceService;
import com.ai.demo.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;
    private final ActiveAudienceService activeAudienceService;

    @GetMapping("/tweets/{conversationId}")
    public ResponseEntity<ThreadConversation> getConversation(@PathVariable Long conversationId) {
        return ResponseEntity.ok(tweetService.getThread(conversationId));
    }

    @GetMapping("/audience/{username}")
    public ResponseEntity<List<ActiveAudience>> getActiveAudience(@PathVariable String username) {
        return ResponseEntity.ok(activeAudienceService.getActiveAudience(username));
    }
}
