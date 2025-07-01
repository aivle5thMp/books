package mp.infra.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookCreatedEvent {
    private UUID bookId;
    private String authorName;
    private String title;
    private String category;
    private String summary;
    private String content;
    private String imageUrl;
    private String audioUrl;
    private Integer todayCount;
    private Integer totalCount;
    private LocalDateTime createdAt;
}
