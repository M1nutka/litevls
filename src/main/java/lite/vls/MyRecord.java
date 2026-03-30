package lite.vls;

import java.time.LocalDate;

public record MyRecord(
    Long id,
    LocalDate date,
    MyTypeCargo typeCargo,
    Integer length,
    Integer width,
    Integer height,
    String placeOfDeparture,
    String deliveryAddress,
    MyStatus status
) {
}