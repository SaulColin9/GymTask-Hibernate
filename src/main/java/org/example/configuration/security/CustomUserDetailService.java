package org.example.configuration.security;

import org.example.dao.jpa.JpaDaoUserImpl;
import org.example.exception.UserAuthenticationException;
import org.example.exception.UserBlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private JpaDaoUserImpl daoUser;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptService.isBlocked()) {
            throw new UserBlockedException("User blocked");
        }
        var foundUser = daoUser.getByUsername(username).orElseThrow();
        UserPrincipal principal = new UserPrincipal();
        principal.setUsername(foundUser.getUsername());
        principal.setPassword(foundUser.getPassword());
        principal.setAuthorities(List.of());
        return principal;
    }
}
