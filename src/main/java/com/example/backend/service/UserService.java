package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.model.userModel.AdminReq;
import com.example.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;



        this.createAdmin();
    }

    public User createUser(String firstname, String lastname, String password, String phone) throws BaseException {

        if (Objects.isNull(password) || Objects.isNull(firstname) || Objects.isNull(lastname)) {
            throw UserException.requestInvalid();
        }
        if (repository.existsByPhone(phone)) {
            throw UserException.createPhoneDuplicated();
        }

        //save data to table user
        User entity = new User();

        entity.setFirstname(firstname);
        entity.setLastname(lastname);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setPhone(phone);
        entity.setRole(User.Role.USER);
        entity.setActive(true);
        entity.setDate(new Date());

        return repository.save(entity);

    }

    public User getUser(String id) throws UserException {
        if (!repository.existsById(id)) {
            throw UserException.notId();
        }
        User entity = new User();
        entity.getEmail();
        return repository.findById(id).get();
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        // check password is match (database and request)

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        return matches;
    }


    public void createAdmin() {

        AdminReq req = new AdminReq();

        User entity;
        if (repository.existsByEmail(req.getEmail())) {
            entity = repository.findByEmail(req.getEmail()).get();
        } else {
            entity = new User();
        }

        entity.setLastname(req.getLastname());
        entity.setFirstname(req.getFirstname());
        entity.setRole(req.getRole());
        entity.setEmail(req.getEmail());
        entity.setDate(new Date());
        entity.setPassword(passwordEncoder.encode(req.getPassword()));
        entity.setPhone(req.getPhone());
        entity.setActive(req.getActive());

        repository.save(entity);

    }

}
