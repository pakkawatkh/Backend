package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String name, String email, String password, String phone) throws BaseException {

        if (Objects.isNull(email)) {
            throw UserException.requestInvalid();
        }
        if (Objects.isNull(password)) {
            throw UserException.requestInvalid();
        }
        if (Objects.isNull(name)) {
            throw UserException.requestInvalid();
        }
        if (userRepository.existsByEmail(email)) {
            throw UserException.createEmailDuplicated();
        }

        //save data to table user
        User entity = new User();
        entity.setEmail(email);
        entity.setName(name);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setPhone(phone);
        entity.setRole(User.Role.USER);
        entity.setActive(true);
        entity.setDate(new Date());
        return userRepository.save(entity);

    }

    public User getUser(String id) throws UserException {
        if (!userRepository.existsById(id)) {
            throw UserException.notId();
        }
        User entity = new User();
        entity.getEmail();
        return userRepository.findById(id).get();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        // check password is match (database and request)

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println(matches);
        return matches;
    }


}
