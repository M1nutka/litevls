package lite.vls.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lite.vls.users.User;
import lite.vls.users.UserEntity;
import lite.vls.users.UserMapper;
import lite.vls.users.UserRepository;
import lite.vls.users.UserRole;

@Service
public class SecurityService implements UserDetailsService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;


    public SecurityService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
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
