package lite.vls.users;

import org.springframework.stereotype.Service;

import lite.vls.transportation.TransportationController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

        public UserService(UserRepository repository, UserMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    private static final Logger log = LoggerFactory.getLogger(TransportationController.class);
    
    public List<UserRecord> getAllUser(){

        List<UserEntityRecord> allUserEntity = repository.findAll();

        List<UserRecord> allUser = allUserEntity.stream()
            .map(it -> mapper.toDomain(it)).toList();

        return allUser;
    }
}
