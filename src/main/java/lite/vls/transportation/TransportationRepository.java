package lite.vls.transportation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransportationRepository extends JpaRepository<TransportationEntityRecord, Long>{
    

    @Modifying
    @Query("""
            update EntityRecord r
            set r.status = :status
            where r.id = :id
            """)
    void setStatus (
        @Param("id") Long id,
        @Param("status")  TransportationStatus myStatus
    );

    @Query("""
            select e
            from EntityRecord e
            where DATE(e.dateField) =:date
            """)
    List<TransportationEntityRecord> findByDate (
        @Param("date") LocalDate date
    );
}
