package mp.infra.dto;

import java.util.UUID;

public class BookListResponseDto {
    private UUID bookId;
    private String authorName;
    private String title;
    private String category;
    private String imageUrl;

    public BookListResponseDto() {}

    public BookListResponseDto(UUID bookId, String authorName, String title,
                               String category, String imageUrl) {
        this.bookId = bookId;
        this.authorName = authorName;
        this.title = title;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
