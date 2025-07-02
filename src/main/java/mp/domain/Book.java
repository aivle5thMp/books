package mp.domain;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    private UUID bookId;
    @Column(name = "authorId")
    private UUID authorId;

    @Column(name = "authorName")
    private String authorName;

    @Column(name = "title")
    private String title;

    @Column(name = "point")
    private Integer point;


    @Column(name = "category")
    private String category;

    @Column(name = "summary")
    private String summary;

    @Column(name = "content")
    private String content;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "audioUrl")
    private String audioUrl;

    @Column(name = "todayCount")
    private Integer todayCount;

    @Column(name = "totalCount")
    private Integer totalCount;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    // Constructors
    public Book() {}

    public Book(UUID bookId, UUID authorId, String authorName, String title, Integer point, String category,
                String summary, String content, String imageUrl, String audioUrl,
                Integer todayCount, Integer totalCount, LocalDateTime createdAt) {
        this.bookId = bookId;
        this.authorId  = authorId;
        this.authorName = authorName;
        this.title = title;
        this.point = point;
        this.category = category;
        this.summary = summary;
        this.content = content;
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
        this.todayCount = todayCount;
        this.totalCount = totalCount;
        this.createdAt = createdAt;
    }

    public static void bookViewIncrease(BookRead event) {
    }

    public static void publishBook(TtsGenerated event) {
    }

    // Getters and Setters
    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }

    public UUID getauthorId() { return authorId; }
    public void setAuthorId(UUID authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }



    // Getter/Setter 모두 필요!
    public Integer getPoint() {
        return point;
    }
    public void setPoint(Integer point) {
        this.point = point;
    }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }

    public Integer getTodayCount() { return todayCount; }
    public void setTodayCount(Integer todayCount) { this.todayCount = todayCount; }

    public Integer getTotalCount() { return totalCount; }
    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    @PrePersist
    public void onPrePersist() {
        if (this.bookId == null) {
            this.bookId = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();
        if (this.todayCount == null) this.todayCount = 0;
        if (this.totalCount == null) this.totalCount = 0;
    }


}

//>>> DDD / Aggregate Root
