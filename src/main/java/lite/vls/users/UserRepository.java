package lite.vls.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
    
    @Query("""
            FROM UserEntity u WHERE u.email = :email
            """)
    UserEntity findByEmail (
        @Param("email") String email
    );
}
