package lite.vls.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lite.vls.users.Response;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    
    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    public Authentication loginUser(Response response){
        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(response.email(), response.password())
        );
    }

}
