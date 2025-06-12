package news_feed_service.repository.cache.user;

import news_feed_service.dto.UserDto;

import java.util.Optional;

public interface UserCacheRepository {

    Optional<UserDto> get(long userId);
}