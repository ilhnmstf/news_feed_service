package news_feed_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ThreadPoolConfig {


    private void loggingInit(String message) {
        log.info("Init thread pool for '{}' method", message);
    }
}