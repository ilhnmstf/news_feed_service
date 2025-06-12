package news_feed_service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "thread-pool-size")
@Component
@Getter
@Setter
public class ThreadPoolProperties {
    private int eventSave;
}
