package news_feed_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import news_feed_service.dto.EventDto;
import news_feed_service.dto.UserDto;
import news_feed_service.properties.RedisProperties;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {
    private final RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig =
                new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        log.info("Init redis connection with host: {}, post: {}, using lettuce",
                redisProperties.getHost(), redisProperties.getPort());
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(redisProperties.getTtl())
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        log.info("Init cache manager with default config {}", defaultConfig);
        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(defaultConfig)
                .build();
    }

    public GenericJackson2JsonRedisSerializer valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    public StringRedisSerializer keySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisTemplate<String, Long> feedTemplate() {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(keySerializer());
        template.setValueSerializer(valueSerializer());
        template.setHashKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setHashValueSerializer(valueSerializer());

        template.afterPropertiesSet();
        log.info("Init post template {}", template);
        return template;
    }

    @Bean
    public RedisTemplate<String, Long> newsFeedRedisTemplate() {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(keySerializer());
        template.setValueSerializer(valueSerializer());
        template.setHashKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setHashValueSerializer(valueSerializer());

        template.afterPropertiesSet();
        log.info("Init news feed redis template {}", template);
        return template;
    }

    @Bean
    public RedisTemplate<String, EventDto> eventRedisTemplate() {
        RedisTemplate<String, EventDto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(keySerializer());
        template.setValueSerializer(valueSerializer());
        template.setHashKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setHashValueSerializer(valueSerializer());

        template.afterPropertiesSet();
        log.info("Init event redis template {}", template);
        return template;
    }

    @Bean
    public RedisTemplate<String, UserDto> userRedisTemplate() {
        RedisTemplate<String, UserDto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(keySerializer());
        template.setValueSerializer(valueSerializer());
        template.setHashKeySerializer(new GenericToStringSerializer<>(Long.class));
        template.setHashValueSerializer(valueSerializer());

        template.afterPropertiesSet();
        log.info("Init user redis template {}", template);
        return template;
    }
}