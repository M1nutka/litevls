package lite.vls;

import java.time.LocalDate;

public record VlsRecord(
    Long id,
    LocalDate date,
    VlsTypeCargo typeCargo,
    int[] gab,
    String placeOfDeparture,
    String deliveryAddress,
    VlsStatus status
) {
}