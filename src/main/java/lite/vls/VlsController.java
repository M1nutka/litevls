package lite.vls;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController

public class VlsController {

    private final VlsService vlsService;

    public VlsController(VlsService vlsService){
        this.vlsService = vlsService;
    }
    
    @GetMapping()
    public List<VlsRecord> getAllRecords() {
        return vlsService.getAllRecord();
    }
    
}