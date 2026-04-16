package lite.vls.transportation;

import org.springframework.stereotype.Component;

@Component
public class TransportationMapper {
    
    public TransportationRecord toDomain(
        TransportationEntityRecord entiti
    ) {
        return new TransportationRecord(
            entiti.getId(),
            entiti.getUser(),
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

    public TransportationEntityRecord toEntity(
        TransportationRecord record
    ) {
        return new TransportationEntityRecord(
            record.id(),
            record.user(),
            record.date(),
            record.typeCargo(),
            record.length(),
            record.width(),
            record.height(),
            record.placeOfDeparture(),
            record.deliveryAddress(),
            record.status()
        );
    }

}
