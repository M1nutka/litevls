package lite.vls.transportation;

import java.time.LocalDate;

import lite.vls.users.UserEntityRecord;


public record TransportationRecord(
    Long id,
    UserEntityRecord user,
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