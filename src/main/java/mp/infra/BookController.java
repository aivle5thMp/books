package mp.infra;


import mp.domain.Book;
import mp.domain.BookRepository;
import mp.infra.dto.*;
import mp.infra.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookListResponseDto>> getAllBooks() {
        try {
            List<BookListResponseDto> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<BookDetailResponseDto> getBookDetail(
            @RequestParam UUID bookId,
            @RequestParam UUID userId) {
        try {
            if (bookId == null || userId == null) {
                return ResponseEntity.badRequest().build();
            }
            //1.책정보 가져오기
            BookDetailRequestDto request = new BookDetailRequestDto();
            request.setBookId(bookId);
            request.setUserId(userId);

            BookDetailResponseDto response = bookService.getBookDetail(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if ("Book not found".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // ✅ 도서 추가 API
    @Autowired
    private KafkaTemplate<String, BookInfoSent> kafkaTemplate;
    @PostMapping("/create")
    public ResponseEntity<String> createBook(@RequestBody BookCreateRequestDto dto) {
        try {
            bookService.saveBook(dto);
            // DTO 정보로 이벤트 생성 (bookId는 없음)
            BookInfoSent event = new BookInfoSent();
            // event.setBookId(null);  // 생성 후 ID를 모르므로 null
            event.setAuthorId(dto.getAuthorId());  // DTO에서 가져옴
            event.setTitle(dto.getTitle());        // DTO에서 가져옴

            kafkaTemplate.send("book.published.v1", event);
            System.out.println("📤 Book 생성 알림 발송 완료: " + event);
            return ResponseEntity.status(HttpStatus.CREATED).body("Book created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create book");
        }
    }


    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/read")
    public ResponseEntity<BookReadResponseDto> readBook(@RequestParam UUID book_id) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(book_id); // ← 여기 OK
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                BookReadResponseDto response = new BookReadResponseDto();

                // 📈 조회수 증가
                if (book.getTodayCount() == null) book.setTodayCount(0);
                if (book.getTotalCount() == null) book.setTotalCount(0);

                book.setTodayCount(book.getTodayCount() + 1);
                book.setTotalCount(book.getTotalCount() + 1);


                response.setContent(book.getContent());     // 이 필드들이 Book에 있어야 함
                response.setAudioUrl(book.getAudioUrl());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


