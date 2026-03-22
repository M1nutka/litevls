package lite.vls;

import java.time.LocalDate;

public record VlsRecord(
    Long id,
    LocalDate date,
    VlsTypeCargo typeCargo,
    VlsDismension gabarit,
    String placeOfDeparture,
    String deliveryAddress,
    VlsStatus status
) {
}