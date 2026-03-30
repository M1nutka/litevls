package lite.vls;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MyService {

    private MyRepository repository;
    
    public MyService(MyRepository repository) {
        this.repository = repository;
    }

    public List<MyRecord> getAllRecord() {
        List<EntityRecord> allEntity = repository.findAll();

        List<MyRecord> mylist = allEntity.stream()
            .map(it ->
                toDomain(it)
            ).toList();
        
        return mylist;
    }

    public MyRecord getById(Long id) {
        EntityRecord enityById = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "No found by id = " + id
            ));
        
        return toDomain(enityById);
    }

    public MyRecord createRecord(MyRecord recordCreate) {
        if (recordCreate.id() != null){
            throw new IllegalArgumentException("Id shold be enpty");
        }

        var newEntity = new EntityRecord(
            null,
            recordCreate.date(),
            recordCreate.typeCargo(),
            recordCreate.length(),
            recordCreate.width(),
            recordCreate.height(),
            recordCreate.placeOfDeparture(),
            recordCreate.deliveryAddress(),
            MyStatus.Waiting
        );

        var newRecord = repository.save(newEntity);
        return toDomain(newRecord);
    }

    @Transactional
    public void cancelRecord(Long id) {
        if (!repository.existsById(id)){
            throw new IllegalArgumentException("Not found record by id = " + id);
        }
        repository.setStatus(id, MyStatus.Canceled);
    }

    public MyRecord updateRecord(
        Long id,
        MyRecord toUpdateRecord
    ){
        var recordById = repository.findById(id)
            .orElseThrow (() -> new EntityNotFoundException(
                    "Not found by id = " + id
            ));
        
        if (recordById.getStatus() != MyStatus.Waiting) {
            throw new IllegalArgumentException("Uncorrect status, status = " + recordById.getStatus());
        }

        var recordToSave = new EntityRecord(
            recordById.getId(),
            toUpdateRecord.date(),
            toUpdateRecord.typeCargo(),
            toUpdateRecord.length(),
            toUpdateRecord.width(),
            toUpdateRecord.height(),
            toUpdateRecord.placeOfDeparture(),
            toUpdateRecord.deliveryAddress(),
            MyStatus.Waiting
        );

        var saveRecord = repository.save(recordToSave);
        return toDomain(saveRecord);
    }

    public MyRecord approveRecord(
        Long id
    ) {
        var recordById = repository.findById(id)
            .orElseThrow (() -> new EntityNotFoundException(
                    "Not found by id = " + id
            ));
        
        if (recordById.getStatus() != MyStatus.Waiting) {
            throw new IllegalArgumentException("Uncorrect status, status = " + recordById.getStatus());
        }

        if (isConflict(recordById)){
            throw new IllegalArgumentException("Cannot approve reservation because of conflict");
        }

        recordById.setStatus(MyStatus.Working);

        repository.save(recordById);
        return toDomain(recordById);
    }

    public boolean isConflict(
        EntityRecord record
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

    private MyRecord toDomain(
        EntityRecord entiti
    ) {
        return new MyRecord(
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
