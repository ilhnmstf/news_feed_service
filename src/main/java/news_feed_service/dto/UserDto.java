package news_feed_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private long id;
    private List<Long> followeeIds;
    private List<Long> followerIds;
}