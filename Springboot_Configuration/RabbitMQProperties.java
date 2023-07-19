package com.fourbench.collaborationservice.configuration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Setter
@Getter
public class RabbitMQProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String virtualHost;

    // constant
    private static final String QUEUE_NAME = "fourbench_general_queue";
    private static final String EXCHANGE_NAME = "fourbench_general_exchange";
    private static final String ROUTING_KEY = "fourbench_general_routing_key";

    public String getQueueName() {
        return QUEUE_NAME;
    }

    public String getExchangeName() {
        return EXCHANGE_NAME;
    }

    public String getRoutingKey() {
        return ROUTING_KEY;
    }
}