package lite.vls.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lite.vls.transportation.TransportationController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(TransportationController.class);
    
    public List<User> getAllUser(){

        List<UserEntity> allUserEntity = repository.findAll();

        List<User> allUser = allUserEntity.stream()
            .map(it -> mapper.toDomain(it)).toList();

        return allUser;
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
            null
        );
    }

    public User loginUser(Response response){
        return new User();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }


}
