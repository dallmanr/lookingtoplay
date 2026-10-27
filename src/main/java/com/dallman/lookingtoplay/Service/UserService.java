package com.dallman.lookingtoplay.service;

import com.dallman.lookingtoplay.authority.Authority;
import com.dallman.lookingtoplay.dto.WebUser;
import com.dallman.lookingtoplay.repository.UserRepository;
import com.dallman.lookingtoplay.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class UserService  {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,  BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(WebUser theWebUser) {
        User user = new User();

        user.setUsername(theWebUser.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(theWebUser.getPassword()));
        user.setFirstName(theWebUser.getFirstName());
        user.setLastName(theWebUser.getLastName());
        user.setEmailAddress(theWebUser.getEmail().toLowerCase());

        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);

        user.setAuthorities(new ArrayList<>(List.of(authority)));

        return userRepository.save(user);
    }
}
