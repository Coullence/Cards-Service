package com.google.cms.controllers;

import com.google.cms.Security.jwt.JwtUtils;
import com.google.cms.Security.refreshtoken.RefreshToken;
import com.google.cms.Security.refreshtoken.RefreshTokenService;
import com.google.cms.models.User;
import com.google.cms.repositories.UserRepository;
import com.google.cms.utilities.Shared.EntityResponse;
import com.google.cms.dtos.LoginRequest;
import com.google.cms.dtos.auth_dto.JwtResponsedto;
import com.google.cms.dtos.res.RoleResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
@RestController
@RequestMapping("/api/v1/token")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping
    public ResponseEntity<?> getToken(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) throws MessagingException {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Username");
        } else {
            // Check if Account is Locked
            if (user.getDeletedFlag() == 'Y') {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This account has been deleted!");
            } else {
                try {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtUtils.generateTokenFromUsername(loginRequest.getUsername());
                    // Return the JWT in the response
                    return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                            "message", "Authentication successful",
                            "jwt", jwt
                    ));
                } catch (AuthenticationException e) {
                    // Return error message for wrong credentials
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials");
                }
            }
        }
    }
}
