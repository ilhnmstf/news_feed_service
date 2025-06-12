package news_feed_service.service.post;

import news_feed_service.dto.EventDto;

import java.util.List;

public interface EventService {

    List<EventDto> getOverCachePosts(long userId, int countInCache, int count);

    List<EventDto> getFolloweePostsWithLimit(long userId, int limit);

    List<EventDto> getByIds(List<Long> eventIds);
}