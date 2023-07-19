package com.fourbench.gatewayservice.configuration;

import com.fourbench.gatewayservice.component.cache.CacheManagerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final JWTConfiguration jwtConfiguration;


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Autowired
    public CacheConfiguration(JWTConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(getDefaultCacheConfiguration())
                .withInitialCacheConfigurations(getCacheConfigurations())
                .build();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost); // Update with your Redis server host
        redisConfig.setPort(redisPort); // Update with your Redis server port
        redisConfig.setPassword(RedisPassword.of(redisPassword)); // Update with your Redis server password

        return new LettuceConnectionFactory(redisConfig);
    }

    private RedisCacheConfiguration getDefaultCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues();
    }

    private Map<String, RedisCacheConfiguration> getCacheConfigurations() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(CacheManagerKey.FOURBENCH_AUTHENTICATION_CACHE.name(), getCacheConfigurationWithExpiration(Duration.ofMillis(jwtConfiguration.getExpirationMs())));
        // Add more cache configurations as needed
        return cacheConfigurations;
    }

    private RedisCacheConfiguration getCacheConfigurationWithExpiration(Duration expiration) {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(expiration);
    }
}