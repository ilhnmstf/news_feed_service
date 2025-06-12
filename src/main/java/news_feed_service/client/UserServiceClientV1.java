package news_feed_service.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import news_feed_service.dto.UserDto;


@FeignClient(name = "user-service", url = "${user-service.host}:${user-service.port}/api/v1/users")
public interface UserServiceClientV1 {

    @Retryable(
            retryFor = {FeignException.FeignServerException.class},
            maxAttempts = 5,
            backoff = @Backoff(value = 500, multiplier = 2)
    )
    @GetMapping("/{userId}/cache")
    UserDto get(@PathVariable long userId);
}