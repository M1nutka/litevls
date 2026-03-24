package lite.vls;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class VlsService {
    
    private final Map<Long, VlsRecord> vlsMap;

    private final AtomicLong idCounter;

    public VlsService() {
        vlsMap = new HashMap<>();
        idCounter = new AtomicLong();
    }

    public List<VlsRecord> getAllRecord() {
        return vlsMap.values().stream().toList();
    }

    public VlsRecord getById(Long id) {
        if (!vlsMap.containsKey(id)){
            throw new IllegalArgumentException("No in Map");
        }
        return vlsMap.get(id);
    }

    public VlsRecord createRecord(VlsRecord vlsToCreate) {
        if (vlsToCreate.id() != null){
            throw new IllegalArgumentException("Id shold be enpty");
        }

        var newVlsRecord = new VlsRecord(
            idCounter.incrementAndGet(),
            vlsToCreate.date(),
            vlsToCreate.typeCargo(),
            vlsToCreate.gabarit(),
            vlsToCreate.placeOfDeparture(),
            vlsToCreate.deliveryAddress(),
            VlsStatus.Waiting
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

    public VlsRecord updateRecord(
        Long id,
        VlsRecord vlsToUpdate
    ){
        if (!vlsMap.containsKey(id)) {
            throw new IllegalArgumentException("No item found in map for id = " + id);
        }

        var record = vlsMap.get(id);
        if (record.status() != VlsStatus.Waiting) {
            throw new IllegalArgumentException("Record status uncorrect, status = " + record.status());
        }

        var updateVlsRecord = new VlsRecord(
            record.id(),
            vlsToUpdate.date(),
            vlsToUpdate.typeCargo(),
            vlsToUpdate.gabarit(),
            vlsToUpdate.placeOfDeparture(),
            vlsToUpdate.deliveryAddress(),
            VlsStatus.Waiting
        );

        vlsMap.put(record.id(), updateVlsRecord);
        return record;
    }

    public VlsRecord approveRecord(
        Long id
    ) {
        if (!vlsMap.containsKey(id)) {
            throw new IllegalArgumentException("No item found in map for id = " + id);
        }

        var record = vlsMap.get(id);
        if (record.status() != VlsStatus.Waiting) {
            throw new IllegalArgumentException("Record status uncorrect, status = " + record.status());
        }

        if (isConflict(record)){
            throw new IllegalArgumentException("Cannot approve reservation because of conflict");
        }

        var approveVlsRecord = new VlsRecord(
            record.id(),
            record.date(),
            record.typeCargo(),
            record.gabarit(),
            record.placeOfDeparture(),
            record.deliveryAddress(),
            VlsStatus.Working
        );

        vlsMap.put(record.id(), approveVlsRecord);
        return record;
    }

    public boolean isConflict(
        VlsRecord record
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
