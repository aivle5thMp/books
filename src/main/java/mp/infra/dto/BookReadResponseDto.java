package mp.infra.dto;

import lombok.Data;

@Data
public class BookReadResponseDto {
    private String content;
    private String audioUrl;
}
