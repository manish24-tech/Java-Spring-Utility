package com.fourbench.collaborationservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

/**
 * Autoconfigured beans with help of properties defined in application.yml
 * 1. ConnectionFactory
 * 2. RabbitTemplate
 * 3. RabbitAdmin
 */
@Configuration
@EnableRabbit
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RabbitMQConfig {
    private final RabbitMQProperties rabbitMQProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQProperties.getHost(), rabbitMQProperties.getPort());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }


    //Required for executing adminstration functions against an AMQP Broker
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /*
     * By default, Spring Boot uses org.springframework.amqp.support.converter.SimpleMessageConverter and serialize the object into byte[].
     * Hence, we have Jackson2JsonMessageConverter to send the message in a JSON format.
     * consumerJackson2MessageConverter(), rabbitTemplate(), messageConverter()
     */
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setRoutingKey(rabbitMQProperties.getRoutingKey());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /*
     * This queue will be declared. This means it will be created if it does not exist. Once declared, you can do something
     * name: my_durable
     * durable: true
     * exclusive: false
     * auto_delete: false
     */

    @Bean
    public Queue queue() {
        return new Queue(
                rabbitMQProperties.getQueueName(),
                true, false, false
        );
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(rabbitMQProperties.getExchangeName());
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(rabbitMQProperties.getRoutingKey());
    }
}