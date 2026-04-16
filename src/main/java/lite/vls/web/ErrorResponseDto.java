package lite.vls.web;

import java.time.LocalDateTime;

public record ErrorResponseDto(
    String massage,
    String detailMassage,
    LocalDateTime errorTime
) {
    
}
