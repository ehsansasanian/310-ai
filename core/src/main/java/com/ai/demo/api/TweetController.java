package com.ai.demo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TweetController {
    @GetMapping("/tweets")
    public ResponseEntity<Object> getTweets(@RequestParam String from, String until, String since) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/replies")
    public ResponseEntity<?> getRepliesByConversationId(@RequestParam Long conversationId, @RequestParam String username) {
        return ResponseEntity.ok(null);
    }
}
