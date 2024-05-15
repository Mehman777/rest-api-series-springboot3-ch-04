package com.tiagoamp.booksapi.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagoamp.booksapi.controller.UserController;
import com.tiagoamp.booksapi.exception.BusinessException;
import com.tiagoamp.booksapi.model.Token;
import com.tiagoamp.booksapi.service.TokenService;
import com.tiagoamp.booksapi.util.Global;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userService;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(Global.AUTH_TOKEN_HEADER_NAME);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = tokenService.getTokenFrom(authorizationHeader);
            String userEmail = tokenService.getSubjectFrom(token);
            UserDetails user = userService.loadUserByUsername(userEmail);
            var authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, null, user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException ex) {
            ex.printStackTrace();  // log error
            response.setHeader("error", ex.getMessage());
            //response.sendError(HttpStatus.FORBIDDEN.value());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }

    }*/
    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String authToken = httpServletRequest.getHeader(Global.AUTH_TOKEN_HEADER_NAME);


        //String authToken = null;
        if(!StringUtils.isEmpty(httpServletRequest.getCookies())){
            for (Cookie cookie : httpServletRequest.getCookies()){
                if(cookie.getName().equals(Global.AUTH_TOKEN_HEADER_NAME)){
                    authToken = cookie.getValue();
                }
            }
        }
        /*if((httpServletRequest.getRequestURI().equals("/entry/login")) && (authToken != null)){
            httpServletResponse.sendRedirect("/");
        }*/




        try {
            if (StringUtils.hasText(authToken)) {
                ServletContext servletContext = httpServletRequest.getServletContext();
                WebApplicationContext webApplicationContext
                        = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                //loginBusinessController = webApplicationContext.getBean(LoginService.class);

                Token userToken = tokenService.getUserTokenByToken(authToken);
                UserDetails userDetails = userService.loadUserByUsername(userToken.getUser().getUsername());

                UsernamePasswordAuthenticationToken token
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (IOException | ServletException | UsernameNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (BusinessException e) {
            LOG.error("Failed into filter AuthTokenFilter UserToken. Cause: ", e);
            if (e.getError().getCode() == BusinessException.BusinessError.TOKEN_NOT_FOUND.getCode()) {
                httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid token.");
                return;
            }
            if (e.getError().getCode() == BusinessException.BusinessError.TIME_EXPIRED.getCode()) {
                httpServletResponse.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Time expire.");
                return;
            }
        }
    }

}
