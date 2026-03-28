package lite.vls;

import java.time.LocalDate;

public record MyRecord(
    Long id,
    LocalDate date,
    MyTypeCargo typeCargo,
    MyDismension gabarit,
    String placeOfDeparture,
    String deliveryAddress,
    MyStatus status
) {
}