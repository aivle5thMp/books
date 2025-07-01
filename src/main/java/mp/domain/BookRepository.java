package mp.domain;  // ✅ com.example.repository가 아닌 mp.domain

import mp.domain.Book;  // ✅ mp.domain.Book으로 수정
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findById(UUID id);

}