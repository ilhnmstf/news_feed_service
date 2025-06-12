package news_feed_service.repository.cache.news_feed;

import news_feed_service.dto.EventDto;

import java.util.List;

public interface NewsFeedCacheRepository {

    void saveAllOptimistic(long userId, List<EventDto> events);

    List<Long> getNext(long userId, long lastViewPostId, int countPosts);

    boolean isLast(long userId, long lastViewPostId);

    boolean isEmpty(long userId);
}