package mp.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mp.BooksApplication;
import mp.config.kafka.KafkaProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;

public class AbstractEvent {

    String eventType;
    Long timestamp;

    public AbstractEvent(Object aggregate) {
        this();
        BeanUtils.copyProperties(aggregate, this);
    }

    public AbstractEvent() {
        this.setEventType(this.getClass().getSimpleName());
        this.timestamp = System.currentTimeMillis();
    }

    public void publish() {
        KafkaProcessor processor = BooksApplication.applicationContext.getBean(
                KafkaProcessor.class
        );
        MessageChannel outputChannel = processor.outboundTopic();

        outputChannel.send(
                MessageBuilder
                        .withPayload(this)
                        .setHeader(
                                MessageHeaders.CONTENT_TYPE,
                                MimeTypeUtils.APPLICATION_JSON
                        )
                        .setHeader("type", getEventType())
                        .build()
        );
    }

    public void publishAfterCommit() {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCompletion(int status) {
                        AbstractEvent.this.publish();
                    }
                }
        );
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean validate() {
        return getEventType().equals(getClass().getSimpleName());
    }

    // ✅ 방법 3: 모든 버전 호환되는 안전한 방법
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 문자열로 직접 설정 (가장 호환성 좋음)
        try {
            objectMapper.setPropertyNamingStrategy(
                    com.fasterxml.jackson.databind.PropertyNamingStrategy.LOWER_CAMEL_CASE
            );
            System.out.println("✅ PropertyNamingStrategy 카멜케이스 설정 성공");
        } catch (Exception e) {
            // 구버전 Jackson인 경우 기본값 사용
            System.out.println("⚠️ PropertyNamingStrategy 설정 실패, 기본값 사용: " + e.getMessage());
        }

        try {
            String json = objectMapper.writeValueAsString(this);
            System.out.println("📤 생성된 JSON: " + json);
            return json;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
    }
}