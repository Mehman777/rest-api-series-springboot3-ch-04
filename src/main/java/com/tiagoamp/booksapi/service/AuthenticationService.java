package com.tiagoamp.booksapi.service;

import com.tiagoamp.booksapi.dto.AuthenticationResponse;
import com.tiagoamp.booksapi.exception.AuthenticationFailedException;
import com.tiagoamp.booksapi.model.AppUser;
import com.tiagoamp.booksapi.model.Role;
import com.tiagoamp.booksapi.repository.UserRepository;
import com.tiagoamp.booksapi.util.RESTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationResponse authenticate(String username, String password) {
        try {
            // The authentication manager provides secure authentication and throws exception if it fails
            var authToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(authToken);
            var user  = (AppUser) authenticate.getPrincipal();
            String token = tokenService.generateToken(user);
            Set<Role> roles= user.getRoles();
            AuthenticationResponse response =  new AuthenticationResponse(username,token,roles);
            return response;
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Invalid User or Password");
        }
    }

}
