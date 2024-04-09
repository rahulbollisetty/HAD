package org.had.consentservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${exchange.name}")
    public String EXCHANGE_NAME;

    @Value("${queue.name}")
    public String QUEUE_NAME;

    @Value("${routingKey.name}")
    public String ROUTING_KEY;

    @Bean
    public DirectExchange customExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue customQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(customQueue()).to(customExchange()).with(ROUTING_KEY);
    }
}
