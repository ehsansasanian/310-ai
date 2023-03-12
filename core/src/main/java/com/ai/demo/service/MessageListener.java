package com.ai.demo.service;

import com.ai.demo.domain.Account;
import com.ai.demo.domain.ReplyMessage;
import com.ai.demo.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static com.ai.demo.config.MQConfig.QUEUE_ONE;
import static com.ai.demo.config.MQConfig.QUEUE_THREE;
import static com.ai.demo.config.MQConfig.QUEUE_TWO;

@Configuration
@RequiredArgsConstructor
public class MessageListener {
    private final AccountRepository accountRepository;
    private final ReplyProcessorService replyProcessorService;
    private final HashMap<String, Account> accountHashMap = new HashMap<>();

    @RabbitListener(queues = QUEUE_ONE)
    public void listener(ReplyMessage message) {
        Account account = accountHashMap.get(QUEUE_ONE);
        if (account == null) {
            account = init(QUEUE_ONE);
            if (account == null) return;
        }
        replyProcessorService.processReply(message, account);
    }

    @RabbitListener(queues = QUEUE_TWO)
    public void listenerTwo(ReplyMessage message) {
        Account account = accountHashMap.get(QUEUE_TWO);
        if (account == null) {
            account = init(QUEUE_TWO);
            if (account == null) return;
        }
        replyProcessorService.processReply(message, account);
    }

    @RabbitListener(queues = QUEUE_THREE)
    public void listenerThree(ReplyMessage message) {
        Account account = accountHashMap.get(QUEUE_THREE);
        if (account == null) {
            account = init(QUEUE_THREE);
            if (account == null) return;
        }
        replyProcessorService.processReply(message, account);
    }

    private Account init(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) return null;
        return accountHashMap.put(username, account);
    }
}
