package news_feed_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class FolloweeEventDto {
    private List<Long> followeeIds;
    private int start;
    private int end;
}