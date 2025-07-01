package mp.domain;

import java.util.*;
import lombok.*;
import mp.domain.*;
import mp.infra.AbstractEvent;

@Data
@ToString
public class BookRead extends AbstractEvent {

    private UUID id;
    private UUID userId;
    private UUID bookId;
}
