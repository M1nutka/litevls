package lite.vls.users;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    private final AuthenticationManager authenticationManager;

        public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
            null,
            "USER",
            true
        );
    }

    public String loginUser(Response response){
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(response.email(), response.password())
            );

            return "Welcome, " + auth.getName() + "!";
            
        } catch (BadCredentialsException e) {
            return "Wrong password!";
            
        } catch (UsernameNotFoundException e) {
            return "User not found!";
        }
    
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity foundUser = repository.findByEmail(email);
        return mapper.toDomain(foundUser);
    }


}
