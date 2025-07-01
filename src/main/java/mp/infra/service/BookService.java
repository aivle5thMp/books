package mp.infra.service;

import mp.domain.Book;
import mp.infra.dto.BookCreateRequestDto;
import mp.infra.dto.BookDetailRequestDto;
import mp.infra.dto.BookDetailResponseDto;
import mp.infra.dto.BookListResponseDto;
import mp.domain.BookRepository;
import mp.domain.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<BookListResponseDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(book -> new BookListResponseDto(
                        book.getBookId(),
                        book.getAuthorName(),
                        book.getTitle(),
                        book.getCategory(),
                        book.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    public BookDetailResponseDto getBookDetail(BookDetailRequestDto request) {
        Optional<Book> bookOptional = bookRepository.findById(request.getBookId());

        if (!bookOptional.isPresent()) {
            throw new RuntimeException("Book not found");
        }

        Book book = bookOptional.get();

        // 구매 여부 확인
        // ✅ 구매 여부 외부 서비스에서 조회
//        String url = String.format("http:/mybook/history/check?userId=%s&bookId=%s",
//                request.getUserId(), request.getBookId());
//
//        Boolean isPurchased = restTemplate.getForObject(url, Boolean.class);
        boolean isPurchased = purchaseRepository.existsByUserIdAndBookId(
                request.getUserId(), request.getBookId());

        return new BookDetailResponseDto(
               // !isPurchased,
                book.getBookId(),
                book.getAuthorName(),
                book.getTitle(),
                book.getCategory(),
                book.getSummary(),
                book.getImageUrl()
        );
    }
    public void saveBook(BookCreateRequestDto dto) {
        Book book = new Book();
        book.setAuthorName(dto.getAuthorName());
        book.setTitle(dto.getTitle());
        book.setCategory(dto.getCategory());
        book.setSummary(dto.getSummary());
        book.setContent(dto.getContent());
        book.setImageUrl(dto.getImageUrl());
        book.setAudioUrl(dto.getAudioUrl());

        // 생성 시 자동 UUID + createdAt 설정됨
        bookRepository.save(book);
    }


}
