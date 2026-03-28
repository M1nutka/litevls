package lite.vls;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRepository extends JpaRepository<EntityRecord, Long>{
    
}
