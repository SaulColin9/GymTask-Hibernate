package org.example.configuration.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT jwt){
        UserPrincipal principal = new UserPrincipal();
        principal.setUsername(jwt.getSubject());
        principal.setPassword(jwt.getClaim("p").asString());
        principal.setAuthorities(List.of());
        return principal;
    }
}
