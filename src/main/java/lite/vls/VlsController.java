package lite.vls;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






@RestController

public class VlsController {

    private final VlsService vlsService;

    public VlsController(VlsService vlsService){
        this.vlsService = vlsService;
    }
    
    @GetMapping()
    public ResponseEntity<List<VlsRecord>> getAllRecords() {
        return ResponseEntity.ok(vlsService.getAllRecord());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VlsRecord> getById(
        @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(vlsService.getById(id));
    }
    

    @PostMapping()
    public ResponseEntity<VlsRecord> creatRecord(
        @RequestBody VlsRecord vlsToCreate
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(vlsService.createRecord(vlsToCreate));
    }
    
    
}