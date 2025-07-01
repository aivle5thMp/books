package mp.domain;

import java.util.*;
import lombok.*;
import mp.domain.*;
import mp.infra.AbstractEvent;

@Data
@ToString
public class TtsGenerated extends AbstractEvent {

    private UUID id;
    private String log;
}
