package news_feed_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto {

    private long id;

    private long ownerId;

    @NotBlank(message = "should not be null or empty")
    private String description;

    @NotBlank(message = "should not be null or empty")
    private String address;

    @NotBlank(message = "should not be null or empty")
    private String city;

    private long countryId;

    @NotBlank(message = "should not be null or empty")
    private String startAt;

    @NotBlank(message = "should not be null or empty")
    private String endAt;

    private String createdAt;
    private String updatedAt;
    private List<Long> participantIds;
}