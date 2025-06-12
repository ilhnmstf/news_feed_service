package news_feed_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import news_feed_service.client.UserServiceClientV1;
import news_feed_service.dto.UserDto;
import news_feed_service.repository.cache.user.UserCacheRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserCacheRepository userCacheRepository;
    private final UserServiceClientV1 userServiceClientV1;

    @Override
    public void validate(long userId) {
        log.debug("Check existing user with id {}", userId);
        get(userId);
    }

    @Override
    public List<Long> getFollowee(long userId) {
        List<Long> followeeIds = get(userId).getFolloweeIds();
        if (followeeIds == null || followeeIds.isEmpty()) {
            throw new RuntimeException("User with id " + userId + " has not followee");
        }
        return followeeIds;
    }


    private UserDto get(long userId) {
        return userCacheRepository.get(userId)
                .orElseGet(() -> userServiceClientV1.get(userId));
    }
}