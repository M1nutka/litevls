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

    public List<TransportationRecord> getAllRecord() {
        List<TransportationEntityRecord> allEntity = repository.findAll();

        List<TransportationRecord> mylist = allEntity.stream()
            .map(it ->
                mapper.toDomain(it)
            ).toList();
        
        return mylist;
    }

    public TransportationRecord getById(Long id) {
        TransportationEntityRecord enityById = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "No found by id = " + id
            ));
        
        return mapper.toDomain(enityById);
    }


    public TransportationRecord createRecord(TransportationRecord recordCreate) {
        if (recordCreate.id() != null){
            throw new IllegalArgumentException("Id shold be enpty");
        }

        var entityToSave = mapper.toEntity(recordCreate);
        entityToSave.setStatus(TransportationStatus.Waiting);

        var newRecord = repository.save(entityToSave);
        return mapper.toDomain(newRecord);
    }

    @Transactional
    public void cancelRecord(Long id) {
        if (!repository.existsById(id)){
            throw new IllegalArgumentException("Not found record by id = " + id);
        }
        repository.setStatus(id, TransportationStatus.Canceled);
    }

    public TransportationRecord updateRecord(
        Long id,
        TransportationRecord toUpdateRecord
    ){
        var recordById = repository.findById(id)
            .orElseThrow (() -> new EntityNotFoundException(
                    "Not found by id = " + id
            ));
        
        if (recordById.getStatus() != TransportationStatus.Waiting) {
            throw new IllegalArgumentException("Uncorrect status, status = " + recordById.getStatus());
        }

        var recordToSave = mapper.toEntity(toUpdateRecord);
        recordToSave.setId(id);
        recordToSave.setStatus(TransportationStatus.Waiting);

        var saveRecord = repository.save(recordToSave);
        return mapper.toDomain(saveRecord);
    }

    public TransportationRecord approveRecord(
        Long id
    ) {
        var recordById = repository.findById(id)
            .orElseThrow (() -> new EntityNotFoundException(
                    "Not found by id = " + id
            ));
        
        if (recordById.getStatus() != TransportationStatus.Waiting) {
            throw new IllegalArgumentException("Uncorrect status, status = " + recordById.getStatus());
        }

        if (isConflict(recordById)){
            throw new IllegalArgumentException("Cannot approve reservation because of conflict");
        }

        recordById.setStatus(TransportationStatus.Working);

        repository.save(recordById);
        return mapper.toDomain(recordById);
    }

    public boolean isConflict(
        TransportationEntityRecord record
    ) {
        LocalDate toDay = LocalDate.now();
        if (record.getDate().isBefore(toDay)){
            return true;
        }
        
        if (record.getPlaceDeparture() == record.getDeliveryAddress()){
            return true;
        }

        return false;
    }

}
