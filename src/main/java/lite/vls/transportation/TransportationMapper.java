package lite.vls.transportation;

import org.springframework.stereotype.Component;

@Component
public class TransportationMapper {
    
    public Transportation toDomain(
        TransportationEntity entiti
    ) {
        return new Transportation(
            entiti.getId(),
            entiti.getUser(),
            entiti.getTodate(),
            entiti.getDate(),
            entiti.getTypeCargo(),
            entiti.getLength(),
            entiti.getWidth(),
            entiti.getHeight(),
            entiti.getPlaceDeparture(),
            entiti.getDeliveryAddress(),
            entiti.getStatus()
        );
    }

    public TransportationEntity toEntity(
        Transportation transportation
    ) {
        return new TransportationEntity(
            transportation.id(),
            transportation.user(),
            transportation.today(),
            transportation.date(),
            transportation.typeCargo(),
            transportation.length(),
            transportation.width(),
            transportation.height(),
            transportation.placeOfDeparture(),
            transportation.deliveryAddress(),
            transportation.status()
        );
    }

}
