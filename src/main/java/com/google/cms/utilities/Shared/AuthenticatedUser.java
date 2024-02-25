package com.google.cms.utilities.Shared;

import com.google.cms.models.User;
import com.google.cms.repositories.UserRepository;
import com.google.cms.utilities.exception.CustomExceptions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticatedUser {
    private final UserRepository userRepository;

    public AuthenticatedUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(){
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<User> userOptional = userRepository.findByUsername(authentication.getName());
            user = userOptional.get();
        }else {
            throw new CustomExceptions.ColorFormatException("User not authenticated!");
        }
        return user;
    }
}
