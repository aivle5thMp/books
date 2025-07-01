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
            UUID bookId = UUID.randomUUID();
            dto.setBookId(bookId);
            bookService.saveBook(dto);
            // 2. 카프카는 별도 try-catch로 처리 (실패해도 책 저장은 성공)
            try {
                BookInfoSent event = new BookInfoSent();
                event.setBookId(bookId);
                event.setAuthorId(dto.getAuthorId());
                event.setTitle(dto.getTitle());

                kafkaTemplate.send("book.published.v1", event);
                System.out.println("📤 카프카 이벤트 발송 성공!");
            } catch (Exception kafkaError) {
                System.err.println("⚠️ 카프카 발송 실패 (하지만 책은 저장됨): " + kafkaError.getMessage());
                // 카프카 실패해도 책 저장은 성공했으므로 계속 진행
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Book created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create book");
        }
    }


    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/read")
    public ResponseEntity<BookReadResponseDto> readBook(@RequestParam("bookId") UUID book_id) {
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
                bookRepository.save(book);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


