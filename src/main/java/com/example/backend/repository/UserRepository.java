package com.example.backend.repository;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

//    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);
    boolean existsByPhone(String phone);

    Optional<User> findById(String id);


    List<User> findAllByRoleIsNot(User.Role role);

    boolean existsById(String id);

    Optional<User> findByShop(Shop shop);

//    boolean existsByEmail(String email);


    @Query("SELECT u.firstname, COUNT(u.firstname) FROM User AS u GROUP BY u.firstname ")
    List<Object[]> countUser();


}
