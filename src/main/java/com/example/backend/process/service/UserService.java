package com.example.backend.process.service;


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

        this.createAdmin();
    }

    public User createUser(String firstname, String lastname, String password, String email) throws BaseException {
        if (repository.existsByEmail(email)) throw UserException.createEmailDuplicated();

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(User.Role.USER);
        user.setActive(true);
        user.setDate(new Date());
        user.setLogin(User.Login.DEFAULT);
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
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void createAdmin() throws BaseException {
        AdminReq req = new AdminReq();
        if (repository.existsByEmail(req.getEmail())) return;

        User user = new User();
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

    public void editUserById(User user, String firstName, String lastName, String facebook, String line) throws BaseException {
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setFacebook(facebook);
        user.setLine(line);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void editEmailById(User user, String email) throws BaseException {
        user.setEmail(email);
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

    public List<User> findAll() {
//        List<User> all = repository.findAll();
        List<User> all = repository.findAllByRoleIsNot(User.Role.ADMIN);
        return all;
    }

    public void saveByUser(User user, String firstname, String lastname, String address, String facebook, String line) throws BaseException {
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAddress(address);
        user.setFacebook(facebook);
        user.setLine(line);
        user.setRegister(true);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public Optional<User> findBySocial(String socialId) {
        Optional<User> bySocialId = repository.findBySocialId(socialId);

        return bySocialId;
    }

    public User saveLoginSocial(String firstname, String lastname, String email, String userId, User.Login login) throws BaseException {
        Optional<User> bySocial = findBySocial(userId);

        User user;
        if (bySocial.isEmpty()) {
            user = new User();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setLogin(login);
            user.setEmail(email);
            user.setSocialId(userId);
            user.setRegister(true);
        } else {
            user = bySocial.get();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setLogin(login);
            user.setEmail(email);
        }
        try {
            repository.save(user);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
        return user;
    }

    public void findByIdActive(String id) throws BaseException {
        if (!repository.existsByIdAndActiveIsTrue(id)) throw MainException.accessDenied();
    }


}
