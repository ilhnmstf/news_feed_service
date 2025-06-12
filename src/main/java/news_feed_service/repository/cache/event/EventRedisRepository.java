package news_feed_service.repository.cache.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import news_feed_service.dto.EventDto;

import java.util.List;

@Repository
@Slf4j
public class EventRedisRepository implements EventCacheRepository {
    private final HashOperations<String, Long, EventDto> eventHashOperations;
    private final String key;

    public EventRedisRepository(RedisTemplate<String, EventDto> eventRedisTemplate) {
        this.eventHashOperations = eventRedisTemplate.opsForHash();
        this.key = "event";
    }

    @Override
    public List<EventDto> getAll(List<Long> postIds) {
        log.debug("Try to get all by keys in redis");
        return eventHashOperations.multiGet(key, postIds);
    }
}