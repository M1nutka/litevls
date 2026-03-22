package lite.vls;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.HashMap;
import java.util.List;

@Service
public class VlsService {
    
    private final Map<Long, VlsRecord> vlsMap;

    private final AtomicLong idCounter;

    public VlsService(){
        vlsMap = new HashMap<>();
        idCounter = new AtomicLong();
    }

    public List<VlsRecord> getAllRecord(){
        return vlsMap.values().stream().toList();
    }
}
