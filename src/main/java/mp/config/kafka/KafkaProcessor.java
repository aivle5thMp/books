package mp.config.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface KafkaProcessor {
    String INPUT = "event-in";
    String OUTPUT = "event-out";
    String BOOK_VIEWED_INPUT = "book-viewed-in";  // ✅ 추가

    @Input(INPUT)
    SubscribableChannel inboundTopic();

    @Output(OUTPUT)
    MessageChannel outboundTopic();

    @Input(BOOK_VIEWED_INPUT) // ✅ 조회수 증가 이벤트 수신용 채널
    SubscribableChannel bookViewedInput();
}
