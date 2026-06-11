package lite.vls.transportation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lite.vls.users.UserEntity;


public record Transportation(
    @Null
    Long id,
    @Null
    UserEntity user,
    @Null
    LocalDateTime today,
    @NotNull
    @FutureOrPresent
    LocalDateTime date,
    @NotNull
    TransportationTypeCargo typeCargo,
    @NotNull
    Integer length,
    @NotNull
    Integer width,
    @NotNull
    Integer height,
    @NotNull
    String placeOfDeparture,
    @NotNull
    String deliveryAddress,
    @Null
    TransportationStatus status
) {
}