package lite.vls.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lite.vls.transportation.TransportationController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(TransportationController.class);
    
    private final UserService service;

    public UserController(UserService service, UserRepository repository, UserMapper mapper){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        log.info("Get all users");
        return ResponseEntity
            .status(200)
            .body(service.getAllUser());
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody @Valid User record) {
        log.info("Create new user");

        return ResponseEntity
            .status(201)
            .body(service.registerUser(record));
    }
    
}
