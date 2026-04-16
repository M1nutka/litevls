package lite.vls.transportation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TransportationService {

    private final TransportationRepository repository;

    private final TransportationMapper mapper;
    
    public TransportationService(TransportationRepository repository, TransportationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Transportation> getAllTransportations() {
        List<TransportationEntity> allEntity = repository.findAll();

        List<Transportation> mylist = allEntity.stream()
            .map(it ->
                mapper.toDomain(it)
            ).toList();
        
        return mylist;
    }

    public Transportation getTransportationById(Long id) {
        TransportationEntity enityById = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "No found by id = " + id
            ));
        
        return mapper.toDomain(enityById);
    }


    public Transportation createTransportation(Transportation transportationCreate) {
        if (transportationCreate.id() != null){
            throw new IllegalArgumentException("Id shold be enpty");
        }

        var entityToSave = mapper.toEntity(transportationCreate);
        entityToSave.setStatus(TransportationStatus.Waiting);

        var newtransportation = repository.save(entityToSave);
        return mapper.toDomain(newtransportation);
    }

    @Transactional
    public void cancelTransportation(Long id) {
        if (!repository.existsById(id)){
            throw new IllegalArgumentException("Not found transportation by id = " + id);
        }
        repository.setStatus(id, TransportationStatus.Canceled);
    }

    public Transportation updateTransportation(
        Long id,
        Transportation toUpdatetransportation
    ){
        var transportationById = repository.findById(id)
            .orElseThrow (() -> new EntityNotFoundException(
                    "Not found by id = " + id
            ));
        
        if (transportationById.getStatus() != TransportationStatus.Waiting) {
            throw new IllegalArgumentException("Uncorrect status, status = " + transportationById.getStatus());
        }

        var transportationToSave = mapper.toEntity(toUpdatetransportation);
        transportationToSave.setId(id);
        transportationToSave.setStatus(TransportationStatus.Waiting);

        var savetransportation = repository.save(transportationToSave);
        return mapper.toDomain(savetransportation);
    }

    public Transportation approveTransportation(
        Long id
    ) {
        var transportationById = repository.findById(id)
            .orElseThrow (() -> new EntityNotFoundException(
                    "Not found by id = " + id
            ));
        
        if (transportationById.getStatus() != TransportationStatus.Waiting) {
            throw new IllegalArgumentException("Uncorrect status, status = " + transportationById.getStatus());
        }

        if (isConflict(transportationById)){
            throw new IllegalArgumentException("Cannot approve reservation because of conflict");
        }

        transportationById.setStatus(TransportationStatus.Working);

        repository.save(transportationById);
        return mapper.toDomain(transportationById);
    }

    public boolean isConflict(
        TransportationEntity transportation
    ) {
        LocalDate toDay = LocalDate.now();
        if (transportation.getDate().isBefore(toDay)){
            return true;
        }
        
        if (transportation.getPlaceDeparture() == transportation.getDeliveryAddress()){
            return true;
        }

        return false;
    }

}
