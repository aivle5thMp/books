package mp.infra.service;

import lombok.RequiredArgsConstructor;
import mp.config.kafka.KafkaProcessor;
import mp.domain.Book;
import mp.domain.BookRepository;
import mp.infra.dto.BookViewed;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookViewEventConsumer {

    private final BookRepository bookRepository;

    @StreamListener(KafkaProcessor.BOOK_VIEWED_INPUT)
    public void onBookViewed(@Payload BookViewed event) {
        if (event.getId() == null) return;

        Optional<Book> optionalBook = bookRepository.findById(event.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTodayCount(book.getTodayCount() + 1);
            book.setTotalCount(book.getTotalCount() + 1);
            bookRepository.save(book);
        }
    }
}
