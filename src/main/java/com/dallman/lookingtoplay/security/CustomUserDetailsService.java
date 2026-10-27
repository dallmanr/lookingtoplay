package com.dallman.lookingtoplay.security;

import com.dallman.lookingtoplay.repository.UserRepository;
import com.dallman.lookingtoplay.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Logger logger = Logger.getLogger(getClass().getName());

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            logger.info("No user found with username: " + username);
            throw new UsernameNotFoundException("user not found: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(appUser.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                        .toList())
                .build();
    }
}
