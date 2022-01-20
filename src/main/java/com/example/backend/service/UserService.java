package com.example.backend.service;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.model.userModel.AdminReq;
import com.example.backend.model.userModel.UserEditReq;
import com.example.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        entity.setLast_password(new Date());


        repository.save(entity);

    }

    public Optional<User> findByPhone(String phone) throws BaseException {
        Optional<User> byEmail = repository.findByPhone(phone);
        if (byEmail.isEmpty()) {
            throw UserException.notFound();
        }
        return byEmail;
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        // check password is match (database and request)

        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        return matches;
    }


    public void createAdmin() {

        AdminReq req = new AdminReq();


        if (repository.existsByPhone(req.getPhone()))
            return;

        User user = new User();

        user.setLastname(req.getLastname());
        user.setFirstname(req.getFirstname());
        user.setRole(req.getRole());
        user.setDate(new Date());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setActive(req.getActive());
        user.setLast_password(new Date());

        repository.save(user);

    }

    public void editUserById(User user, UserEditReq req) {

        user.setFirstname(req.getFirstname());
        user.setLastname(req.getLastname());
        user.setFacebook(req.getFacebook());
        user.setLine(req.getLine());

        repository.save(user);

    }

    public void editPhoneById(User user, UserEditReq req) throws BaseException {
        if (req.getPhone() == null) {
            throw UserException.requestInvalid();
        }
        user.setPhone(req.getPhone());
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
