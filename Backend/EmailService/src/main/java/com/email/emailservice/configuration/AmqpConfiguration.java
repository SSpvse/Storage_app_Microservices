package com.email.emailservice.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class AmqpConfiguration {

    // Exchange
    @Bean
    public TopicExchange notificationTopicExchange(@Value("${amqp.exchange.name}") final String exchangeName) {

        return ExchangeBuilder
                .topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    // queues
    @Bean
    public Queue storeDtoQueue(@Value("${amqp.queue.name}") final String queueName) {
        return QueueBuilder
                .durable(queueName)
                .build();
    }



    // Binding
    @Bean
    public Binding notificationBinding(final Queue notificationQueue, final TopicExchange notificationTopicExchange){
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationTopicExchange)
                .with("item.date.bindkey"); // TODO CHANGE KEY
    }


    // JSON converter for message handling
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
        jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    // Listener
    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(final MessageHandlerMethodFactory messageHandlerMethodFactory){
        return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }

}
