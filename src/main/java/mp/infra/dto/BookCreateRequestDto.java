package mp.infra.dto;

import mp.domain.Book;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookCreateRequestDto {

    private UUID authorId;
    private String authorName;
    private String title;
    private int price;
    private String category;
    private String summary;
    private String content;
    private String imageUrl;
    private String audioUrl;

    // 기본 생성자
    public BookCreateRequestDto() {}
    public UUID getAuthorId() {
        return authorId;
    }
    public UUID setAuthorId() {
        return authorId;
    }
    // Getters & Setters
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    // 👉 Book 엔티티로 변환하는 메서드
    public Book toEntity() {
        return new Book(
                UUID.randomUUID(),
                this.authorId,// 자동 생성 UUID
                this.authorName,
                this.title,
                this.price,
                this.category,
                this.summary,
                this.content,
                this.imageUrl,
                this.audioUrl,
                0,                             // todayCount 초기값
                0,                             // totalCount 초기값
                LocalDateTime.now()           // 생성 시각
        );
    }
}
