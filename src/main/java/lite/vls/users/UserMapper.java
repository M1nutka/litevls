package lite.vls.users;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserRecord toDomain(
        UserEntityRecord entiti
    ) {
        return new UserRecord(
            entiti.getId(),
            entiti.getName(),
            entiti.getLastname(),
            entiti.getPhone(),
            entiti.getEmail(),
            entiti.getPassword()
        );
    }

    public UserEntityRecord toEntity(
        UserRecord record
    ) {
        return new UserEntityRecord(
            record.id(),
            record.name(),
            record.lastname(),
            record.phone(),
            record.email(),
            record.password()
        );
    }

}


