package news_feed_service.repository.cache.news_feed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsFeedOptimisticOperation {//todo add retry

    private final RedisTemplate<String, Long> newsFeedRedisTemplate;

    public Boolean addOptimistic(String key, Runnable doing) {
        log.debug("Try to update value with key {}", key);
        return newsFeedRedisTemplate.execute(new SessionCallback<>() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                log.debug("Monitoring the changes in the value in key {}", key);
                operations.watch(key);
                log.debug("Start redis transaction");
                operations.multi();
                doing.run();
                Object res = operations.exec();
                log.debug("Finish redis transaction");

                return res != null;
            }
        });
    }
}