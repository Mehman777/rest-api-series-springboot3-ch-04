package com.tiagoamp.booksapi.controller;

import com.tiagoamp.booksapi.dto.AppUserRequest;
import com.tiagoamp.booksapi.dto.AppUserResponse;
import com.tiagoamp.booksapi.dto.AuthenticationRequest;
import com.tiagoamp.booksapi.dto.AuthenticationResponse;
import com.tiagoamp.booksapi.enums.Keys;
import com.tiagoamp.booksapi.exception.BusinessException;
import com.tiagoamp.booksapi.exception.ExceptionUtil;
import com.tiagoamp.booksapi.model.AppUser;
import com.tiagoamp.booksapi.service.AuthenticationService;
import com.tiagoamp.booksapi.service.TokenService;
import com.tiagoamp.booksapi.service.UserService;
import com.tiagoamp.booksapi.util.Global;
import com.tiagoamp.booksapi.util.RESTResponse;
import com.tiagoamp.booksapi.util.UserMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;
    private final AuthenticationService authenticationService;
    private final UserMapper mapper;

    private final AuthenticationManager authenticationManager;


    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<AppUserResponse>> getUsers() {
        List<AppUser> users = userService.find();
        var resp = users.stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    @RolesAllowed("ADMIN") // needs to enable 'EnableGlobalMethodSecurity' at security class to work
    public ResponseEntity<AppUserResponse> createUser(@RequestBody @Valid AppUserRequest request) {
        var user = mapper.toModel(request);
        user = userService.save(user);
        var resp = mapper.toResponse(user);
        return ResponseEntity.created(URI.create(user.getId().toString())).body(resp);
    }

/*    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request, HttpServletResponse res) {
        var response = authenticationService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }*/
@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
@PreAuthorize("permitAll()")
public ResponseEntity login(@RequestBody AuthenticationRequest loginDTO, HttpServletResponse res) {
    RESTResponse<AuthenticationResponse> response = new RESTResponse<>();
    HttpServletResponse httpServletResponse = (HttpServletResponse) res;

    try {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Map map = authenticationService.login(username, password);
        String token = map.get(Keys.TOKEN).toString();
        AppUser user = (AppUser) map.get(Keys.USER);

        httpServletResponse.setHeader(Global.AUTH_TOKEN_HEADER_NAME, token);

        Cookie cookie = new Cookie(Global.AUTH_TOKEN_HEADER_NAME, token);
        cookie.setPath("/");
        cookie.setMaxAge(Global.MAX_AGE_COOKIE);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setUsername(user.getUsername());
        authenticationResponse.setRoles(user.getRoles());

        UsernamePasswordAuthenticationToken tokenAuth
                = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authenticationManager.authenticate(tokenAuth);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.setMessage(Global.OPERATION_COMPLETED_SUCCESSFULLY);
        response.setSuccess(Boolean.TRUE);
        response.setData(authenticationResponse);

    } catch (BusinessException e) {
        LOG.error("Failed login user. Cause: ", e);
        ExceptionUtil.mapToResponse(response, e);
    }
    return ResponseEntity.ok(response);
}

    @PostMapping(value = "/logout")
    @PreAuthorize("permitAll()")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        String authToken = null;
        if (session != null) {

            session.invalidate();
        }

        for (Cookie cookie : request.getCookies()) {

            if (cookie.getName().equals(Global.AUTH_TOKEN_HEADER_NAME)) {
                authToken = cookie.getValue();
                try {
                    tokenService.deleteToken(authToken);
                } catch (BusinessException e) {
                    System.err.println(e);
                }
            }
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
        return "logout";
    }

}
