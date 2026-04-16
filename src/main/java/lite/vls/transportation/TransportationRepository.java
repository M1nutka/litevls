package lite.vls.transportation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransportationRepository extends JpaRepository<TransportationEntity, Long>{
    

    @Modifying
    @Query("""
            update TransportationEntity r
            set r.status = :status
            where r.id = :id
            """)
    void setStatus (
        @Param("id") Long id,
        @Param("status")  TransportationStatus myStatus
    );

}
