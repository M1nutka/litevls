package lite.vls;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class VlsController {

    private final VlsService vlsService;

    public VlsController(VlsService vlsService){
        this.vlsService = vlsService;
    }
    
}