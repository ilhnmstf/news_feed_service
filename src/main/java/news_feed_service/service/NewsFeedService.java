package news_feed_service.service;

import news_feed_service.dto.EventDto;

import java.util.List;

public interface NewsFeedService {

    List<EventDto> getNext(long userId, long lastViewPostId);
}