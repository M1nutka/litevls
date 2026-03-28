package lite.vls;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    
    private final Map<Long, MyRecord> vlsMap;

    private final AtomicLong idCounter;

    public MyService() {
        vlsMap = new HashMap<>();
        idCounter = new AtomicLong();
    }

    public List<MyRecord> getAllRecord() {
        return vlsMap.values().stream().toList();
    }

    public MyRecord getById(Long id) {
        if (!vlsMap.containsKey(id)){
            throw new IllegalArgumentException("No in Map");
        }
        return vlsMap.get(id);
    }

    public MyRecord createRecord(MyRecord vlsToCreate) {
        if (vlsToCreate.id() != null){
            throw new IllegalArgumentException("Id shold be enpty");
        }

        var newVlsRecord = new MyRecord(
            idCounter.incrementAndGet(),
            vlsToCreate.date(),
            vlsToCreate.typeCargo(),
            vlsToCreate.gabarit(),
            vlsToCreate.placeOfDeparture(),
            vlsToCreate.deliveryAddress(),
            MyStatus.Waiting
        );

        vlsMap.put(newVlsRecord.id(), newVlsRecord);
        return newVlsRecord;
    }

    public void deleteRecord(Long id) {
        if (!vlsMap.containsKey(id)){
            throw new IllegalArgumentException("Not found record by id = " + id);
        }
        vlsMap.remove(id);
    }

    public MyRecord updateRecord(
        Long id,
        MyRecord vlsToUpdate
    ){
        if (!vlsMap.containsKey(id)) {
            throw new IllegalArgumentException("No item found in map for id = " + id);
        }

        var record = vlsMap.get(id);
        if (record.status() != MyStatus.Waiting) {
            throw new IllegalArgumentException("Record status uncorrect, status = " + record.status());
        }

        var updateVlsRecord = new MyRecord(
            record.id(),
            vlsToUpdate.date(),
            vlsToUpdate.typeCargo(),
            vlsToUpdate.gabarit(),
            vlsToUpdate.placeOfDeparture(),
            vlsToUpdate.deliveryAddress(),
            MyStatus.Waiting
        );

        vlsMap.put(record.id(), updateVlsRecord);
        return record;
    }

    public MyRecord approveRecord(
        Long id
    ) {
        if (!vlsMap.containsKey(id)) {
            throw new IllegalArgumentException("No item found in map for id = " + id);
        }

        var record = vlsMap.get(id);
        if (record.status() != MyStatus.Waiting) {
            throw new IllegalArgumentException("Record status uncorrect, status = " + record.status());
        }

        if (isConflict(record)){
            throw new IllegalArgumentException("Cannot approve reservation because of conflict");
        }

        var approveVlsRecord = new MyRecord(
            record.id(),
            record.date(),
            record.typeCargo(),
            record.gabarit(),
            record.placeOfDeparture(),
            record.deliveryAddress(),
            MyStatus.Working
        );

        vlsMap.put(record.id(), approveVlsRecord);
        return record;
    }

    public boolean isConflict(
        MyRecord record
    ) {
        LocalDate toDay = LocalDate.now();
        if (record.date().isBefore(toDay)){
            return true;
        }
        
        if (record.placeOfDeparture() == record.deliveryAddress()){
            return true;
        }

        return false;
    }
}
