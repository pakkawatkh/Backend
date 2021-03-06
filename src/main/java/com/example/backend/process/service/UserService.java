package com.example.backend.process.service;


import com.example.backend.entity.Base.RandomString;
import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.UserException;
import com.example.backend.model.adminModel.AdminReq;
import com.example.backend.process.repository.UserRepository;
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

    public UserService(UserRepository userRepository, ShopService shopService, PasswordEncoder passwordEncoder) throws BaseException {
        this.repository = userRepository;
        this.shopService = shopService;
        this.passwordEncoder = passwordEncoder;
//        deleteByRegisterIsFalse();
        this.createAdmin();
    }

    public User createUser(String firstname, String lastname, String password, String email) throws BaseException {
        String number = new RandomString().number();
        if (repository.existsByNumber(number)) this.createUser(firstname,lastname,password,email);
        if (repository.existsByEmail(email)) throw UserException.createEmailDuplicated();

        User user = new User();
        user.setNumber(number);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setLogin(User.Login.DEFAULT);
        user.setLast_password(new Date());
        try {
            return repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public User findByEmail(String email) throws BaseException {
        Optional<User> byEmail = repository.findByEmail(email);
        if (byEmail.isEmpty()) throw UserException.notFound();

        return byEmail.get();
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return !passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void createAdmin() throws BaseException {

        AdminReq req = new AdminReq();
        if (repository.existsByEmail(req.getEmail())) return;

        User user = new User();
        user.setNumber(req.getNumber());
        user.setLastname(req.getLastname());
        user.setFirstname(req.getFirstname());
        user.setRole(req.getRole());
        user.setDate(new Date());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setActive(req.getActive());
        user.setLogin(User.Login.DEFAULT);
        user.setRegister(true);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void editUserById(User user, String firstName, String lastName,String address,Long lat,Long lng,String province,String district,String picture) throws BaseException {
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setLat(lat);
        user.setLng(lng);
        user.setAddress(address);
        user.setDistrict(district);
        user.setProvince(province);
        user.setPicture(picture);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void updateRole(User user, User.Role role) throws BaseException {
        user.setRole(role);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public User findById(String id) throws BaseException {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) throw UserException.notFound();

        return user.get();
    }

    public User findByNumber(String number) throws BaseException {
        Optional<User> user = repository.findByNumber(number);
        if (user.isEmpty()) throw UserException.notFound();

        return user.get();
    }

    public User findByShop(Integer id) throws BaseException {
        Shop shop = shopService.findById(id);

        return shop.getUser();
    }

    public void updateUserActive(User user, Boolean active) throws MainException {
        user.setActive(active);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void updatePassword(User user, String password) throws BaseException {
        user.setPassword(passwordEncoder.encode(password));
        user.setLast_password(new Date());
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public List<User> findAllByRoleIsNot() {
        return repository.findAllByRoleIsNot(User.Role.ADMIN);
    }

    public void saveEditByUser(User user, String firstname, String lastname, String address) throws BaseException {
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAddress(address);
        user.setRegister(true);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public Optional<User> findBySocial(String socialId) {
        return repository.findBySocialId(socialId);
    }

    public User saveLoginSocial(String firstname, String lastname, String userId, User.Login login) throws BaseException {
        Optional<User> bySocial = findBySocial(userId);

        User user;
        if (bySocial.isEmpty()) {
            String number = new RandomString().number();

            if (repository.existsByNumber(number)) this.saveLoginSocial(firstname,lastname,userId,login);

            user = new User();
            user.setNumber(number);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setLogin(login);
            user.setSocialId(userId);
            user.setRegister(true);
        } else {
            user = bySocial.get();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setLogin(login);
        }
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
        return user;
    }

    public void RegisterActive(User user) throws BaseException {
        user.setRegister(true);
        user.setLast_password(new Date());
        try {
            repository.save(user);
        }catch (Exception e){
            throw MainException.errorSave();
        }
    }

    public List<User> fileAll(){
        return repository.findAll();
    }
    public void deleteByUser(User user){
        repository.delete(user);
    }
}
