package com.dallman.lookingtoplay.security;

import com.dallman.lookingtoplay.repository.UserRepository;
import com.dallman.lookingtoplay.service.UserService;
import com.dallman.lookingtoplay.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserRepository userRepository;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        // Get the user from the database using the UserService
        User theUser = userRepository.findByUsername(username);
        // Get the session
        HttpSession session = request.getSession();
        // Add the user to the session
        session.setAttribute("user", theUser);
        // Forward to home page
        response.sendRedirect(request.getContextPath() + "/games/");
    }
}
