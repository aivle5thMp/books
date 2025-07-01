package mp.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    // 또는 더 간단하게:
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "userId")
    private UUID userId;

    @Column(name = "bookId")
    private UUID bookId;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    // Constructors
    public Purchase() {}

    public Purchase(UUID userId, UUID bookId, LocalDateTime createdAt) {
        this.userId = userId;
        this.bookId = bookId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}