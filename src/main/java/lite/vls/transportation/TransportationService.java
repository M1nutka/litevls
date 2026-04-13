package lite.vls.transportation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TransportationService {

    private final TransportationRepository repository;
    
    public TransportationService(TransportationRepository repository) {
        this.repository = repository;
    }

    public List<TransportationRecord> getAllRecord() {
        List<TransportationEntityRecord> allEntity = repository.findAll();

        List<TransportationRecord> mylist = allEntity.stream()
            .map(it ->
                toDomain(it)
            ).toList();
        
        return mylist;
    }

    public TransportationRecord getById(Long id) {
        TransportationEntityRecord enityById = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "No found by id = " + id
            ));
        
        return toDomain(enityById);
    }

    public List<TransportationRecord> getByNowDay(LocalDate date) {
        List<TransportationEntityRecord> allEntity = repository.findByDate(date);
        List<TransportationRecord> myList = allEntity.stream()
            .map(it ->
                toDomain(it)
            ).toList();

        return myList;
    }

    public TransportationRecord createRecord(TransportationRecord recordCreate) {
        if (recordCreate.id() != null){
            throw new IllegalArgumentException("Id shold be enpty");
        }

        var newEntity = new TransportationEntityRecord(
            null,
            recordCreate.date(),
            recordCreate.typeCargo(),
            recordCreate.length(),
            recordCreate.width(),
            recordCreate.height(),
            recordCreate.placeOfDeparture(),
            recordCreate.deliveryAddress(),
            TransportationStatus.Waiting
        );

        var newRecord = repository.save(newEntity);
        return toDomain(newRecord);
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

        var recordToSave = new TransportationEntityRecord(
            recordById.getId(),
            toUpdateRecord.date(),
            toUpdateRecord.typeCargo(),
            toUpdateRecord.length(),
            toUpdateRecord.width(),
            toUpdateRecord.height(),
            toUpdateRecord.placeOfDeparture(),
            toUpdateRecord.deliveryAddress(),
            TransportationStatus.Waiting
        );

        var saveRecord = repository.save(recordToSave);
        return toDomain(saveRecord);
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
        return toDomain(recordById);
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

    private TransportationRecord toDomain(
        TransportationEntityRecord entiti
    ) {
        return new TransportationRecord(
            entiti.getId(),
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
}
