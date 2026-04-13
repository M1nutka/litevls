package lite.transportation;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;







@RestController
@RequestMapping("/record")
public class TransportationController {

    private final TransportationService vlsService;

    private static final Logger log = LoggerFactory.getLogger(TransportationController.class);

    public TransportationController(TransportationService vlsService){
        this.vlsService = vlsService;
    }
    
    @GetMapping()
    public ResponseEntity<List<TransportationRecord>> getAllRecords() {
        log.info("Get all records");
        return ResponseEntity.ok(vlsService.getAllRecord());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportationRecord> getById(
        @PathVariable("id") Long id
    ) {
        try {
            log.info("Get by id = " + id);
            return ResponseEntity.ok()
                .body(vlsService.getById(id));
        } catch (NoSuchElementException e) {
            log.error("No such by id = " + id);
            return ResponseEntity.status(404)
                .build();
        }
    }
    

    @PostMapping()
    public ResponseEntity<TransportationRecord> creatRecord(
        @RequestBody TransportationRecord vlsToCreate
    ) {
        log.info("Created new record");
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(vlsService.createRecord(vlsToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportationRecord> updateRecord(
        @PathVariable("id") Long id,
        @RequestBody TransportationRecord vlsToUpdate
    ) {
        try {
            vlsService.updateRecord(id, vlsToUpdate);
            log.info("update record by id = " + id);
            return ResponseEntity.ok()
                .build();
        } catch (NoSuchElementException e) {
            log.error("No such record by id = " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
        }
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(
        @PathVariable("id") Long id
    ) {
        try {
            vlsService.cancelRecord(id);
            log.info("Delete record id = " + id);
            return ResponseEntity.ok()
                .build();
        } catch (NoSuchElementException e) {
            log.error("No such record by id = " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
        }
    }

    @PostMapping("approve/{id}")
    public ResponseEntity<TransportationRecord> approveRecord(
        @PathVariable("id") Long id
    ) {
        try {
            vlsService.approveRecord(id);
            log.info("Approve record id = " + id);
            return ResponseEntity.ok()
                .build();
        } catch (NoSuchElementException e) {
            log.error("No such record by id = " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
        }
    }
}