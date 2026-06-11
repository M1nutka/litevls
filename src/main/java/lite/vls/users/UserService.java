package lite.vls.users;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;


    public UserService(UserRepository repository, UserMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }


    public List<User> getAllUsers(){
        List<UserEntity> allUsersEntity = repository.findAll();

        List<User> userList = allUsersEntity.stream()
        .map(
            it -> mapper.toDomain(it)
        ).toList();
        
        return userList;
    }

    public User getUser(Long id){
        UserEntity user = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
                "No found by id = " + id
            ));

        return mapper.toDomain(user);
    }
    
}
