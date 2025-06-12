package news_feed_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import news_feed_service.dto.UserDto;


@FeignClient(name = "user-service", url = "${user-service.host}:${user-service.port}/api/v1/users")
public interface UserServiceClientV1 {

    // todo add retry
    @GetMapping("/{userId}/cache")
    UserDto get(@PathVariable long userId);
}