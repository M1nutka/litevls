package lite.vls.users;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(
        UserEntity user
    ) {
        return new User(
            user.getId(),
            user.getName(),
            user.getLastname(),
            user.getPhone(),
            user.getEmail(),
            user.getPassword(),
            user.getRole(),
            user.getActive()
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
            record.password(),
            record.role(),
            record.active()
        );
    }

}


