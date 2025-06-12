package news_feed_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import news_feed_service.dto.EventDto;
import news_feed_service.properties.NewsFeedProperties;
import news_feed_service.repository.cache.news_feed.NewsFeedCacheRepository;
import news_feed_service.service.post.EventService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedServiceImpl implements NewsFeedService {
    private final EventService eventService;
    private final NewsFeedCacheRepository newsFeedCacheRepository;
    private final NewsFeedProperties newsFeedProperties;

    @Override
    public List<EventDto> getNext(long userId, long lastViewPostId) {
        if (newsFeedCacheRepository.isEmpty(userId)) {
            return fillNewsFeedAndGet(userId);
        }

        if (newsFeedCacheRepository.isLast(userId, lastViewPostId)) {
            return eventService.getOverCachePosts(userId, newsFeedProperties.getCountInCache(), newsFeedProperties.getCount());
        }

        return eventService.getByIds(
                newsFeedCacheRepository.getNext(userId, lastViewPostId, newsFeedProperties.getCount()));
    }

    private List<EventDto> fillNewsFeedAndGet(long userId) {
        List<EventDto> events = eventService.getFolloweePostsWithLimit(userId, newsFeedProperties.getCountInCache());
        newsFeedCacheRepository.saveAllOptimistic(userId, events);
        return events.subList(0, newsFeedProperties.getCount());
    }
}