package lite.vls.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lite.vls.transportation.TransportationController;
import lite.vls.users.Response;
import lite.vls.users.User;

import org.springframework.security.core.Authentication;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(TransportationController.class);
    
    private final AuthService authService;

    private final SecurityService securityService;

    public SecurityController(AuthService authService, SecurityService securityService){
        this.authService = authService;
        this.securityService = securityService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User record) {
        log.info("Create new user");

        return ResponseEntity
            .status(201)
            .body(securityService.registerUser(record));
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
