package com.example.backend.service;


import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.model.userModel.AdminReq;
import com.example.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final ShopService shopService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ShopService shopService, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.shopService = shopService;
        this.passwordEncoder = passwordEncoder;

        this.createAdmin();

    }

    public void createUser(String firstname, String lastname, String password, String phone) throws BaseException {

        if (repository.existsByPhone(phone)) throw UserException.createPhoneDuplicated();

        User entity = new User();

        entity.setFirstname(firstname);
        entity.setLastname(lastname);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setPhone(phone);
        entity.setRole(User.Role.USER);
        entity.setActive(true);
        entity.setDate(new Date());
        entity.setLast_password(new Date());
        entity.setLogin(User.Login.DEFAULT);

        repository.save(entity);
    }

    public User findByPhone(String phone) throws BaseException {

        Optional<User> byPhone = repository.findByPhone(phone);

        if (byPhone.isEmpty()) throw UserException.notFound();

        return byPhone.get();
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    public void createAdmin() {

        AdminReq req = new AdminReq();

        if (repository.existsByPhone(req.getPhone())) return;

        User user = new User();

        user.setLastname(req.getLastname());
        user.setFirstname(req.getFirstname());
        user.setRole(req.getRole());
        user.setDate(new Date());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setActive(req.getActive());
        user.setLast_password(new Date());
        user.setLogin(User.Login.DEFAULT);

        repository.save(user);

    }

    public void editUserById(User user, String firstName, String lastName, String facebook, String line) {

        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setFacebook(facebook);
        user.setLine(line);

        repository.save(user);

    }

    public void editPhoneById(User user, String phone) throws BaseException {

        user.setPhone(phone);
        repository.save(user);

    }

    public void updateRole(User user, User.Role role) {
        user.setRole(role);
        repository.save(user);
    }

    public User findById(String id) throws BaseException {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            throw UserException.notFound();
        }
        return user.get();
    }

    public User findByShop(Integer id) throws BaseException {
        Shop shop = shopService.findById(id);
//        Optional<User> user = repository.findByShop(shop);
//
//        if (user.isEmpty()){
//            throw UserException.notFound();
//        }
        return shop.getUser();
    }

    public void updateUserActive(User user, Boolean active) {
        user.setActive(active);
        repository.save(user);
    }

    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        user.setLast_password(new Date());
        repository.save(user);
    }

    public List<User> findAll() {
//        List<User> all = repository.findAll();
        List<User> all = repository.findAllByRoleIsNot(User.Role.ADMIN);
        return all;
    }

    public void saveByUser(User user, String firstname, String lastname, String address, String facebook, String line) {

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAddress(address);
        user.setFacebook(facebook);
        user.setLine(line);
        repository.save(user);
    }


}
