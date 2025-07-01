package mp.infra;

import lombok.Data;
import java.util.UUID;

@Data
public class BookInfoSent {
    private UUID bookId;
    private UUID authorId;
    private String title;
}
