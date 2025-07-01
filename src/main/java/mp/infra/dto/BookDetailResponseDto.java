package mp.infra.dto;

import java.util.UUID;

public class BookDetailResponseDto {
    //private Boolean status;
    private UUID bookId;
    private String authorName;
    private String title;
    private String category;
    private String summary;
    private String imageUrl;

    public BookDetailResponseDto() {}

    public BookDetailResponseDto(
            //Boolean status,
            UUID bookId, String authorName,
                                 String title, String category, String summary, String imageUrl) {
        //this.status = status;
        this.bookId = bookId;
        this.authorName = authorName;
        this.title = title;
        this.category = category;
        this.summary = summary;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
//    public Boolean getStatus() { return status; }
//    public void setStatus(Boolean status) { this.status = status; }

    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }


}