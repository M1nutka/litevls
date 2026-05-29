package lite.vls.users;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
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
    
    public User registerUser(User newUser) {
        
        String hashPassword = passwordEncoder.encode(newUser.password());
        UserEntity newEntityUser = mapper.toEntity(newUser);
        newEntityUser.setPassword(hashPassword);
        repository.save(newEntityUser);
        User userToSave = mapper.toDomain(newEntityUser);
        return new User(
            userToSave.id(),
            userToSave.name(),
            userToSave.lastname(),
            userToSave.phone(),
            userToSave.email(),
            null,
            UserRole.USER,
            true
        );
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Searching for user: " + email);
        
        UserEntity foundUser = repository.findByEmail(email);
        
        if (foundUser == null) {
            System.out.println("User NOT found!");
            throw new UsernameNotFoundException("User not found: " + email);
        }
        
        System.out.println("User found: " + foundUser.getEmail());
        System.out.println("Stored password hash: " + foundUser.getPassword());
        
        return mapper.toDomain(foundUser);
    }




}
