package com.example.backend.process.repository;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByUser(User user);

    Optional<Orders> findByIdAndUser(Integer integer, User user);

    List<Orders> findAllByUser(User user);

    Page<Orders> findAllByStatus(Orders.Status status,Pageable pageable);

    boolean existsAllByType(Type type);

    boolean existsByIdAndUser(Integer id,User user);

    //select by user and page
    List<Orders> findAllByUserOrderByDateDesc(User user, Pageable pageable);

    //count order by user
    @Query(value = "SELECT count(o) FROM Orders as o where o.user = :user")
     Long count(@Param("user") User user);
}
