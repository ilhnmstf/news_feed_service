package news_feed_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import news_feed_service.dto.EventDto;
import news_feed_service.dto.FolloweeEventDto;

import java.util.List;

@FeignClient(name = "event-service", url = "${event-service.host}:${event-service.port}/api/v1/events")
public interface EventServiceClientV1 {

    @GetMapping("/followee")
    List<EventDto> getFolloweeEvent(@RequestBody FolloweeEventDto followeeEventDto);

    @GetMapping
    List<EventDto> getByIds(@RequestBody List<Long> eventIds);
}