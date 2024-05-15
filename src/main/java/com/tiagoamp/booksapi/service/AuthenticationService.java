package com.tiagoamp.booksapi.service;

import com.tiagoamp.booksapi.dto.AuthenticationResponse;
import com.tiagoamp.booksapi.enums.Keys;
import com.tiagoamp.booksapi.exception.AuthenticationFailedException;
import com.tiagoamp.booksapi.exception.BusinessException;
import com.tiagoamp.booksapi.exception.ExceptionMapper;
import com.tiagoamp.booksapi.model.AppUser;
import com.tiagoamp.booksapi.model.Role;
import com.tiagoamp.booksapi.repository.UserRepository;
import com.tiagoamp.booksapi.util.Global;
import com.tiagoamp.booksapi.util.RESTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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

    public Map login(String username, String password) throws BusinessException {
        Map result = null;
        try {
                    AppUser user = userRepository.findByUsernameIgnoreCase(username);

            if (null == user) {
                throw new BusinessException(BusinessException.BusinessError.USER_NOT_FOUND);
            }
            if (!user.isEnabled()) {
                throw new BusinessException(BusinessException.BusinessError.ACCOUNT_IS_NOT_ACTIVATED);
            }
            //istifadəçi adı mövcuddursa şifrənin bazada olan şifrə ilə üzləşdirilməsi
            if (!passwordEncoder.matches(password, user.getPassword())) {

                throw new BusinessException(BusinessException.BusinessError.INCORRECT_PASSWORD);
            }

            result = new HashMap();
            String token = tokenService.getToken(user);
            result.put(Keys.USER, user);
            result.put(Keys.TOKEN, token);
        } catch (Exception e) {
            throw ExceptionMapper.map(e);
        }
        return result;
    }

}
