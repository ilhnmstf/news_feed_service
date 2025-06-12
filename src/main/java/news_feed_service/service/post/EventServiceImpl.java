package news_feed_service.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import news_feed_service.client.EventServiceClientV1;
import news_feed_service.dto.EventDto;
import news_feed_service.dto.FolloweeEventDto;
import news_feed_service.repository.cache.event.EventCacheRepository;
import news_feed_service.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final UserService userService;
    private final EventServiceClientV1 eventServiceClient;
    private final EventCacheRepository eventCacheRepository;

    @Override
    public List<EventDto> getOverCachePosts(long userId, int countInCache, int count) {
        return getFolloweeEvent(userId, countInCache, count);
    }

    @Override
    public List<EventDto> getFolloweePostsWithLimit(long userId, int limit) {
        userService.validate(userId);
        return getFolloweeEvent(userId, 0, limit);
    }

    @Override
    public List<EventDto> getByIds(List<Long> eventIds) {
        List<EventDto> events = eventCacheRepository.getAll(eventIds);
        if (events.size() < eventIds.size()) {
            events = eventServiceClient.getByIds(eventIds);
        }
        return events;
    }

    private List<EventDto> getFolloweeEvent(long userId, int start, int end) {
        return eventServiceClient.getFolloweeEvent(
                new FolloweeEventDto()
                        .setFolloweeIds(userService.getFollowee(userId))
                        .setStart(start)
                        .setEnd(end));
    }
}