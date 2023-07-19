package com.fourbench.collaborationservice.configuration;

import com.fourbench.collaborationservice.websocket.stomp.UserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final RabbitMQProperties rabbitMQProperties;

    // Register the WebSocket endpoint for clients to connect
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // custom heartbeat, every 60 sec instead of default 25 sec
        registry.addEndpoint("/fourbench-websocket-endpoint")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * Configure the message broker with application and user destination prefix to use RabbitMe
     *
     * @param registry org.springframework.messaging.simp.config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // Set the application destination prefix
        registry.setApplicationDestinationPrefixes("/app");

        // Set the user destination prefix
        registry.setUserDestinationPrefix("/users");

        // Enable STOMP broker relay with full-featured message broker - RabbitMQ, ActiveMQ, Apache kafka
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost(rabbitMQProperties.getHost())
                .setRelayPort(61613)
                .setClientLogin(rabbitMQProperties.getUsername())
                .setClientPasscode(rabbitMQProperties.getPassword())
                .setSystemHeartbeatSendInterval(10000)  // Set the interval for sending heartbeats (in milliseconds) - WebSocket
                .setSystemHeartbeatReceiveInterval(10000);  // Set the interval for receiving heartbeats (in milliseconds) - WebSocket
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new UserInterceptor());
    }
}