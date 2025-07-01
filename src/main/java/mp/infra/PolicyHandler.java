package mp.infra;

import mp.config.kafka.KafkaProcessor;
import mp.domain.Book;
import mp.domain.BookRead;
import mp.domain.BookRepository;
import mp.infra.BookInfoSent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private KafkaTemplate<String, BookInfoSent> kafkaTemplate;

    @Value("${kafka.topics.book-info-sent}")
    private String bookInfoTopic;

    @StreamListener(
            value = KafkaProcessor.INPUT,
            condition = "headers['type']=='BookRead'"
    )
    public void wheneverBookRead_BookViewIncrease(@Payload BookRead bookRead) {
        System.out.println("\n\n##### listener BookViewIncrease : " + bookRead + "\n\n");

        // 기존 로직
        Book.bookViewIncrease(bookRead);

        // 📌 bookId 기반 책 정보 가져오기
        Book book = bookRepository.findById(bookRead.getBookId())
                .orElse(null);
        if (book != null) {
            BookInfoSent event = new BookInfoSent();
            event.setBookId(book.getBookId());
            event.setAuthorId(book.getauthorId());
            event.setTitle(book.getTitle());

            kafkaTemplate.send(bookInfoTopic, event);
            System.out.println("📤 BookInfoSent 이벤트 발행 완료: " + event);
        }
    }
}
