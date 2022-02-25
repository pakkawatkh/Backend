package com.example.backend.process.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.UserException;
import com.example.backend.process.repository.UserRepository;
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

    @Value("${app.login-social.user-id}")
    private String userId;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String tokenize(User user, tokenizeType type) {
        Calendar calendar = Calendar.getInstance();

        if (type.equals(tokenizeType.LOGIN_DEFAULT)) calendar.add(Calendar.MINUTE, 60 * 24 * 7);
        if (type.equals(tokenizeType.REGISTER)) calendar.add(Calendar.MINUTE, 5);
        if (type.equals(tokenizeType.LOGIN_SOCIAL)) calendar.add(Calendar.SECOND, 5);

        Date expiresAt = calendar.getTime();

        return JWT.create().withIssuer(issuer).withClaim("principal", user.getId()).withClaim("lastpass", user.getLast_password().toString()).withExpiresAt(expiresAt).sign(algorithm());
    }

    public String tokenizeSocial(User user) {
        return tokenize(user, tokenizeType.LOGIN_SOCIAL);
    }

    public String tokenizeLogin(User user) {
        return tokenize(user, tokenizeType.LOGIN_DEFAULT);
    }

    public String tokenizeRegister(User user) {
        return tokenize(user, tokenizeType.REGISTER);
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

    public void checkLoginSocial() throws BaseException {
        String userId = this.userId();
        if (!userId.equals(this.userId)) throw UserException.notFound();
    }

    public User getUserByToken() throws BaseException {
        String userId = this.userId();
        String date = this.lastPassword();

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw UserException.notFound();
        if (!user.get().getRegister()) throw UserException.confirmAccount();
        if (!date.equals("[" + user.get().getLast_password() + "]")) throw MainException.expires();
        if (!user.get().getActive()) throw MainException.accessDenied();

        return user.get();
    }

    public User getUserByTokenRegister() throws BaseException {
        String userId = this.userId();
        String date = this.lastPassword();

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw UserException.notFound();
        if (!date.equals("[" + user.get().getLast_password() + "]")) throw MainException.expires();

        return user.get();
    }

    public void checkAdminByToken() throws BaseException {
        String userId = this.userId();
        String date = this.lastPassword();

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw UserException.notFound();
        if (!user.get().getRegister()) throw UserException.confirmAccount();
        if (!date.equals("[" + user.get().getLast_password() + "]")) throw MainException.expires();
        if (!user.get().getActive()) throw MainException.accessDenied();
        if (user.get().getRole() != User.Role.ADMIN) throw MainException.accessDenied();
    }

    public void checkUserByToken() throws BaseException {
        String userId = this.userId();
        boolean check = userRepository.existsByIdAndActiveIsTrue(userId);
        if (!check) throw MainException.accessDenied();
    }

    public String userId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return (String) authentication.getPrincipal();
    }

    public String lastPassword() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getAuthorities().toString();
    }

    public enum tokenizeType {
        LOGIN_DEFAULT, LOGIN_SOCIAL, REGISTER
    }
}
