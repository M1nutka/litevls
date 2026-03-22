package lite.vls;

import java.time.LocalDate;

public record VlsRecord(
    Long id,
    LocalDate date,
    VlsTypeCargo type,
    String placeOfDeparture,
    String deliveryAddress,
    VlsStatus status
) {
}