package lite.vls.transportation;

import java.time.LocalDate;

public record TransportationRecord(
    Long id,
    LocalDate date,
    TransportationTypeCargo typeCargo,
    Integer length,
    Integer width,
    Integer height,
    String placeOfDeparture,
    String deliveryAddress,
    TransportationStatus status
) {
}