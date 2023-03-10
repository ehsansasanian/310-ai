package com.ai.demo.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix = "scrapper")
public class ScrapperConfiguration {
    @Getter
    private final Properties properties = new Properties();
    @Getter
    @Setter
    public static class Properties {
        private String baseUrl;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
