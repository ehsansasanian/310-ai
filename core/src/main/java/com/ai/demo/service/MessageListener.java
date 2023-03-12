package com.ai.demo.service;

import com.ai.demo.domain.Account;
import com.ai.demo.domain.ReplyMessage;
import com.ai.demo.repo.AccountRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.ai.demo.config.MQConfig.QUEUE_ONE;
import static com.ai.demo.config.MQConfig.QUEUE_THREE;
import static com.ai.demo.config.MQConfig.QUEUE_TWO;

@Configuration
@RequiredArgsConstructor
public class MessageListener {
    private final HashMap<String, Account> accountMap = new HashMap<>();
    private final AccountRepository accountRepository;
    private final ReplyProcessorService replyProcessorService;
    private final static List<String> queues = Arrays.asList(QUEUE_ONE, QUEUE_TWO, QUEUE_THREE);

    @PostConstruct
    public void inti() {
        accountRepository.findAll().forEach(account -> {
            if (queues.contains(account.getUsername())) {
                accountMap.put(account.getUsername(), account);
            }
        });
    }

    @RabbitListener(queues = QUEUE_ONE)
    public void listener(ReplyMessage message) {
        replyProcessorService.processReply(message, accountMap.get(QUEUE_ONE));
    }

    @RabbitListener(queues = QUEUE_TWO)
    public void listenerTwo(ReplyMessage message) {
        replyProcessorService.processReply(message, accountMap.get(QUEUE_TWO));
    }

    @RabbitListener(queues = QUEUE_THREE)
    public void listenerThree(ReplyMessage message) {
        replyProcessorService.processReply(message, accountMap.get(QUEUE_THREE));
    }
}
