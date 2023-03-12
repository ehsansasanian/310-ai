package com.ai.demo.service;

import com.ai.demo.config.ScrapperConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ScrapperGatewayService {
    private final String url;
    private final RestTemplate restTemplate;

    public ScrapperGatewayService(ScrapperConfiguration scrapperConfiguration) {
        this.url = scrapperConfiguration.getProperties().getBaseUrl();
        this.restTemplate = scrapperConfiguration.getRestTemplate();
    }

    public Object getTweetsByQuery(String query) {
        try {
            ResponseEntity<Object> exchange = restTemplate.exchange(
                    String.format("http://%s?query=%s", url + "/tweets", query), HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()), Object.class);

            return exchange.getBody();

        } catch (Exception e) {
            log.error("exception occurred during fetching tweets: {}", e.getMessage());
        }
        return null;
    }

    // Since there is a limit in Snscrape, this function is not used in the code.
    // Leave it here, might be useful later.
    public Object getRepliesByConversationId(Long conversationId, String username) {
        try {
            log.info("calling scraper to fetch replies for user = {} and conv {} ", username, conversationId);
            var fullURL = "http://" + url + "/replies?conversationId=" + conversationId + "&username=" + username;
            ResponseEntity<Object> exchange = restTemplate.exchange(
                    fullURL, HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()), Object.class);
            return exchange.getBody();
        } catch (Exception e) {
            log.error("exception occurred during fetching replies: {}", e.getMessage());
        }
        return null;
    }
}
