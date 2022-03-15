package com.example.backend.process.repository;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByNumber(String number);

    boolean existsByNumber(String number);

    Optional<User> findByEmail(String phone);

    boolean existsByEmail(String phone);

    Optional<User> findById(String id);

    List<User> findAllByRoleIsNot(User.Role role);

    boolean existsById(String id);

    Optional<User> findByShop(Shop shop);

    Optional<User> findBySocialId(String socialId);

    boolean existsByIdAndActiveIsTrue(String is);

    @Query("SELECT u.firstname, COUNT(u.firstname) FROM User AS u GROUP BY u.firstname ")
    List<Object[]> countUser();


    @Query(value = "SELECT u.picture FROM User as u")
    String[] getAllPicture();
}
