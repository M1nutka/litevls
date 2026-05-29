package lite.vls.transportation;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;









@RestController
@RequestMapping("/transportation")
public class TransportationController {

    private final TransportationService service;


    private static final Logger log = LoggerFactory.getLogger(TransportationController.class);

    public TransportationController(TransportationService service){
        this.service = service;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<List<Transportation>> getAllTransportations() {
        log.info("Get all transportations");
        return ResponseEntity.ok(service.getAllTransportations());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Transportation> getById(
        @PathVariable("id") Long id
    ) {
        log.info("Get by id = " + id);
        return ResponseEntity.ok()
            .body(service.getTransportationById(id));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Transportation>> getByUser(
        Authentication authentication
    ) {
        log.info("Get by user id = " + authentication.getName());
        return ResponseEntity.ok()
            .body(service.getTransportationByUser(authentication.getName()));
    }
    

    @PostMapping("/user")
    public ResponseEntity<Transportation> createTransportation(
        @RequestBody Transportation transportationToCreate
    ) {
        log.info("Created new transportation");
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.createTransportation(transportationToCreate));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Transportation> updateTransportation(
        @PathVariable("id") Long id,
        @RequestBody Transportation transportationToUpdate
    ) {
        service.updateTransportation(id, transportationToUpdate);
        log.info("update transportation by id = " + id);
        return ResponseEntity.ok()
            .build();

    }
    

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteTransportationUser(
        @PathVariable("id") Long id
    ) {
        service.cancelTransportationUser(id);
        log.info("Delete transportation id = " + id);
        return ResponseEntity.ok()
            .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteTransportationAdmin(
        @PathVariable("id") Long id
    ) {
        service.cancelTransportationAdmin(id);
        log.info("Delete transportation id = " + id);
        return ResponseEntity.ok()
            .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/approve/{id}")
    public ResponseEntity<Transportation> approveTransportation(
        @PathVariable("id") Long id
    ) {
        service.approveTransportation(id);
        log.info("Approve transportation id = " + id);
        return ResponseEntity.ok()
            .build();
    }


    
}