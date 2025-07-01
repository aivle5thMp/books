package mp.infra.dto;


import java.util.UUID;

public class BookDetailRequestDto {
    private UUID bookId;
    private UUID userId;

    public BookDetailRequestDto() {}

    public BookDetailRequestDto(UUID bookId, UUID userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    // Getters and Setters
    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}
