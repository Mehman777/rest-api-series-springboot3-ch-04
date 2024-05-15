package com.tiagoamp.booksapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tiagoamp.booksapi.exception.BusinessException;
import com.tiagoamp.booksapi.exception.ExceptionMapper;
import com.tiagoamp.booksapi.model.AppUser;
import com.tiagoamp.booksapi.model.Role;
import com.tiagoamp.booksapi.model.Token;
import com.tiagoamp.booksapi.repository.UserTokenRepository;
import com.tiagoamp.booksapi.util.GeneralUtil;
import com.tiagoamp.booksapi.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${secret.key}")
    private String secret;
    private final UserDetailsService userService;

    private final UserTokenRepository userTokenRepository;
    public String getTokenFrom(String bearerToken) {
        System.out.println("******************************");
        System.out.println(bearerToken);
        System.out.println("****************************");
        final String bearer = "Bearer ";
        if (bearerToken == null || !bearerToken.startsWith(bearer))
            throw new JWTVerificationException("Invalid Authorization Header");
        String token = bearerToken.substring(bearer.length());
        return token;
    }
    public Token getUserTokenByToken(String token) throws BusinessException {
        long time = System.currentTimeMillis();
        Token userToken;

        try {
            userToken = userTokenRepository.findByToken(token);

            if (null == userToken) {
                throw new BusinessException(BusinessException.BusinessError.TOKEN_NOT_FOUND);
            }
            /* expire_date yoxlanılır,əgər istifadə müddəti bitibsə
            token-ə uygun məlumatlar USER_TOKEN table-dan silinir
            əks halda expire_date yenilənir */
            if (!GeneralUtil.expired(userToken.getExpireDate().getTime())) {
                userTokenRepository.delete(userToken);
                throw new BusinessException(BusinessException.BusinessError.TIME_EXPIRED);
            } else {

                long expires = time + Global.TOKEN_EXPIRATION_TIME;
                userToken.setLastUsed(new Date(time));
                userToken.setExpireDate(new Date(expires));
                userToken = userTokenRepository.save(userToken);
            }
        } catch (Exception e) {
            throw ExceptionMapper.map(e);
        }
        return userToken;
    }

    public String getSubjectFrom(String token) {
      Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT decodedJWT = verifier.verify(token);  // throws JWTVerificationException if not valid
      return decodedJWT.getSubject();
    }

    public String generateToken(AppUser user) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        Instant expiration = generateExpirationTimeIn(10);  // expires in 10 min
        List<Role> roles = new ArrayList<>();
        for (Role i : user.getRoles())
            roles.add(i);
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiration)
                .withIssuer("Smarthome-API")
                //.withClaim("roles", roles)
                .sign(algorithm);
        return token;
    }

    private Instant generateExpirationTimeIn(int minutes) {
        return LocalDateTime.now().plusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant();
    }
    public String getToken(AppUser user) throws BusinessException {
        long time = System.currentTimeMillis();
        long expires = time + Global.TOKEN_EXPIRATION_TIME;
        String token = null;
        try {
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

            token = generateToken(user);

            Token ut = new Token();

            Token u = userTokenRepository.findByUser(user);
            if (!StringUtils.isEmpty(u)) {
               ut=u;
               ut.setId(u.getId());
            }
            ut.setToken(token);
            ut.setExpireDate(new Date(expires));
            ut.setUser(user);

            userTokenRepository.save(ut);

        } catch (Exception e) {
            throw ExceptionMapper.map(e);
        }
        return token;
    }
    public boolean deleteToken(String userToken) throws BusinessException {
        boolean result = false;
        try {
            Token token;
            // göndərilən token-ə görə istifadəçi token-in mövcudluğu yoxlanılır
            token = userTokenRepository.findByToken(userToken);
            if (!StringUtils.isEmpty(token)) {
                userTokenRepository.delete(token);
                result = true;
            }
            // göndərilən token mövcuddursa token bazadan silinir
            return result;
        } catch (Exception e) {
            throw ExceptionMapper.map(e);
        }
    }

//    public DecodedJWT getDecodedTokenFrom(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        DecodedJWT decodedJWT = verifier.verify(token);
//        return decodedJWT;
//    }

//    public String generateAccessToken(AppUser user) {
//        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
//        String accessToken = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))  // expires in 10 min
//                .withIssuer("Books-API")
//                .withClaim("roles", user.getRole().name())
//                .sign(algorithm);
//        return accessToken;
//    }
//
//    public String generateRefreshToken(AppUser user) {
//        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
//        String refreshToken = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))  // expires in 60 min
//                .withIssuer("Books-API")
//                .sign(algorithm);
//        return refreshToken;
//    }

}
