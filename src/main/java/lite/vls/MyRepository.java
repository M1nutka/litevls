package lite.vls;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyRepository extends JpaRepository<EntityRecord, Long>{
    

    @Modifying
    @Query("""
            update EntityRecord r
            set r.status = :status
            where r.id = :id
            """)
    void setStatus (
        @Param("id") Long id,
        @Param("status")  MyStatus myStatus
    );

    @Query("""
            select e
            from EntityRecord e
            where DATE(e.dateField) =:date
            """)
    List<EntityRecord> findByDate (
        @Param("date") LocalDate date
    );
}
