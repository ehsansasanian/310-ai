package com.ai.demo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    public static final String QUEUE_ONE = "alikarimi_ak8";
    public static final String QUEUE_TWO = "TaylorLorenz";
    public static final String QUEUE_THREE = "ylecun";
    public static final String EXCHANGE = "reply_exchange";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE, false, false);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public Queue queueOne() {
        return new Queue(QUEUE_ONE, false);
    }

    @Bean
    public Binding bindingOne() {
        return BindingBuilder
                .bind(queueOne())
                .to(exchange())
                .withQueueName();
    }

    @Bean
    public Queue queueTwo() {
        return new Queue(QUEUE_TWO, false);
    }

    @Bean
    public Binding bindingTwo() {
        return BindingBuilder
                .bind(queueTwo())
                .to(exchange())
                .withQueueName();
    }

    @Bean
    public Queue queueThree() {
        return new Queue(QUEUE_THREE, false);
    }

    @Bean
    public Binding bindingThree() {
        return BindingBuilder
                .bind(queueThree())
                .to(exchange())
                .withQueueName();
    }

}
