package news_feed_service.repository.cache.event;

import news_feed_service.dto.EventDto;

import java.util.List;

public interface EventCacheRepository {
    List<EventDto> getAll(List<Long> postIds);
}