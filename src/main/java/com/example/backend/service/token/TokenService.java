package com.example.backend.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    private final UserRepository userRepository;
    @Value("${app.token.secret}")
    private String secret;

    @Value("${app.token.issuer}")
    private String issuer;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String tokenize(User user) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60 * 24 * 7);
        Date expiresAt = calendar.getTime();

        String token = JWT.create().withIssuer(issuer)
                .withClaim("principal", user.getId())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(expiresAt)
                .sign(algorithm());

        //return token type String
        return token;
    }

    public DecodedJWT verify(String token) {
        try {

            JWTVerifier verifier = JWT.require(algorithm()).withIssuer(issuer).build(); //Reusable verifier instance
            return verifier.verify(token);

        } catch (Exception e) {
            return null;
        }

    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    public User getUserByToken() throws BaseException {
        String userId = this.userId();

        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        if (!opt.get().getActive()) {
            throw UserException.accessDenied();
        }
        User user = opt.get();
        return user;
    }

    public void checkAdminByToken() throws BaseException {
        String userId = this.userId();

        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        if (!opt.get().getActive()) {
            throw UserException.accessDenied();
        }
        if (opt.get().getRole()!= User.Role.ADMIN){
            throw UserException.accessDenied();
        }
    }

    public String userId(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userId = (String) authentication.getPrincipal();
        return userId;
    }

}
