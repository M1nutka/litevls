package lite.vls.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lite.vls.security.AuthService;
import lite.vls.transportation.TransportationController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(TransportationController.class);
    
    private final UserService service;

    private final AuthService authService;

    public UserController(UserService service, AuthService authService){
        this.service = service;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User record) {
        log.info("Create new user");

        return ResponseEntity
            .status(201)
            .body(service.registerUser(record));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid Response response) {
        log.info("Login attempt for: {}", response.email());
        
        try {
            Authentication auth = authService.loginUser(response);
            log.info("Login successful for: {}", auth.getName());
            
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "email", auth.getName()
            ));
            
        } catch (BadCredentialsException e) {
            log.warn("Login failed - bad credentials for: {}", response.email());
            return ResponseEntity.status(401).body(Map.of(
                "error", "Invalid email or password"
            ));
            
        } catch (UsernameNotFoundException e) {
            log.warn("Login failed - user not found: {}", response.email());
            return ResponseEntity.status(401).body(Map.of(
                "error", "User not found"
            ));
        }
    
    }
}
