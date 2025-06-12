package news_feed_service.repository.cache.news_feed;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import news_feed_service.dto.EventDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Repository
public class NewsFeedRedisRepository  implements NewsFeedCacheRepository {
    private final NewsFeedOptimisticOperation newsFeedOptimisticOperation;
    private final RedisTemplate<String, Long> newsFeedRedisTemplate;
    private final ZSetOperations<String, Long> newsFeedZSetOperation;
    private final ExecutorService saveEventPool;
    private final String key;

    public NewsFeedRedisRepository(
            NewsFeedOptimisticOperation newsFeedOptimisticOperation,
            RedisTemplate<String, Long> newsFeedRedisTemplate,
            ExecutorService saveEventPool) {
        this.newsFeedRedisTemplate = newsFeedRedisTemplate;
        this.newsFeedOptimisticOperation = newsFeedOptimisticOperation;
        this.newsFeedZSetOperation = newsFeedRedisTemplate.opsForZSet();
        this.saveEventPool = saveEventPool;
        this.key = "feed:";
    }

    public void saveOptimistic(long userId, EventDto event) {
        CompletableFuture.runAsync(() ->
                newsFeedOptimisticOperation
                        .addOptimistic(key + userId, () -> save(userId, event)), saveEventPool);
    }

    @Override
    public void saveAllOptimistic(long userId, List<EventDto> events) {
        events.forEach(event -> saveOptimistic(userId, event));
    }

    @Override
    public List<Long> getNext(long userId, long lastViewPostId, int countPosts) {
        long start = lastViewPostId < 1 ? 0 : indexOf(userId, lastViewPostId);
        return new ArrayList<>(Objects.requireNonNull(
                newsFeedZSetOperation.reverseRange(key + userId, start, start + countPosts)));
    }

    @Override
    public boolean isLast(long userId, long lastViewPostId) {
        validateKey(userId);
        Set<Long> last = newsFeedZSetOperation.reverseRange("scores", -1, -1);

        return last != null && last.contains(lastViewPostId);
    }

    @Override
    public boolean isEmpty(long userId) {
        return !hasKey(userId) || 1 > newsFeedZSetOperation.size(key + userId);
    }

    private Boolean save(long userId, EventDto event) {
        return newsFeedZSetOperation.add(key + userId, event.getId(), getScore(event));
    }

    private double getScore(EventDto event) {
        return LocalDateTime.parse(event.getCreatedAt()).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    protected Long indexOf(long userId, long eventId) {
        validateKey(userId);

        Long idx = newsFeedZSetOperation.rank(key + userId, eventId);
        if (idx == null) {
            throw new IllegalArgumentException("value " + userId + " in key" + key + eventId + " not exists");
        }

        return idx;
    }

    private void validateKey(long userId) {
        if (!hasKey(userId)) {
            throw new IllegalArgumentException("key " + key + userId + " not exists");
        }
    }

    private boolean hasKey(long userId) {
        return newsFeedRedisTemplate.hasKey(key + userId);
    }
}