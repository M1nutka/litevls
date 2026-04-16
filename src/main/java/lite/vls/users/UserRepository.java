package lite.vls.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntityRecord, Long>{
    
}
