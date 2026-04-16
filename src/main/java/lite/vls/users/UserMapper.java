package lite.vls.users;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(
        UserEntity entiti
    ) {
        return new User(
            entiti.getId(),
            entiti.getName(),
            entiti.getLastname(),
            entiti.getPhone(),
            entiti.getEmail(),
            entiti.getPassword()
        );
    }

    public UserEntity toEntity(
        User record
    ) {
        return new UserEntity(
            record.id(),
            record.name(),
            record.lastname(),
            record.phone(),
            record.email(),
            record.password()
        );
    }

}


