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

    List<Orders> findAllByUserAndStatusOrderByDateDesc(User user, Orders.Status status);

    Optional<Orders> findByIdAndUser(Integer integer, User user);

    @Query(value = "SELECT * FROM Orders WHERE Orders.status =:status ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Orders> randomByStatusLimit(@Param("status") String status, @Param("limit") Integer limit);

    List<Orders> findAllByUser(User user);

    Page<Orders> findAllByStatus(Orders.Status status, Pageable pageable);

    boolean existsAllByType(Type type);

    boolean existsByIdAndUser(Integer id, User user);

    //select by user and page
    List<Orders> findAllByUserOrderByDateDesc(User user, Pageable pageable);

    //count order by user
    @Query(value = "SELECT count(o) FROM Orders as o where o.user = :user")
    Long count(@Param("user") User user);


    //Filter

    //---------------type null , province null-------------//

    List<Orders> findAllByStatusOrderByDateDesc(Orders.Status status, Pageable pageable);

    List<Orders> findAllByStatusOrderByDateAsc(Orders.Status status, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o where  o.status = :status GROUP BY o.type")
    List<Object> getAllType(@Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o where o.status = :status GROUP BY o.province")
    List<Object> getAllProvince(@Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where  o.status = :status")
    Long countAllBuy(@Param("status") Orders.Status status);


    //--------------province null, type not null--------------//

    List<Orders> findAllByStatusAndTypeOrderByDateDesc(Orders.Status status, Type type, Pageable pageable);

    List<Orders> findAllByStatusAndTypeOrderByDateAsc(Orders.Status status, Type type, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o where  o.status = :status and o.type =:type GROUP BY o.type")
    Object getByType(@Param("type") Type type,@Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o where o.status = :status and o.type =:type GROUP BY o.province")
    List<Object> getAllProvinceByType(@Param("type") Type type,@Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where o.type = :type and o.status = :status")
    Long countAllByType(@Param("type") Type type, @Param("status") Orders.Status status);




    //--------------type null, province not null--------------//

    List<Orders> findAllByStatusAndProvinceOrderByDateDesc(Orders.Status status, String province, Pageable pageable);

    List<Orders> findAllByStatusAndProvinceOrderByDateAsc(Orders.Status status, String province, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o where  o.status = :status and o.province = :province GROUP BY o.type")
    List<Object> getAllTypeByProvince(@Param("province") String province,@Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o where o.status = :status and o.province = :province GROUP BY o.province")
    Object getByProvince(@Param("province") String province,@Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where o.province = :province and o.status = :status")
    Long countAllByProvince(@Param("province") String province, @Param("status") Orders.Status status);




    //-------------type not null, province not null---------------//

    List<Orders> findAllByStatusAndTypeAndProvinceOrderByDateDesc(Orders.Status status, Type type, String province, Pageable pageable);

    List<Orders> findAllByStatusAndTypeAndProvinceOrderByDateAsc(Orders.Status status, Type type, String province, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o  where o.province = :province and o.type = :type and o.status = :status GROUP BY o.type")
    Object getTypeByTypeAndProvince(@Param("type") Type type, @Param("province") String province, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o  where o.province = :province and o.type = :type and o.status = :status GROUP BY o.province")
    Object getProvinceByTypeAndProvince(@Param("type") Type type, @Param("province") String province, @Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where o.province = :province and o.type = :type and o.status = :status")
    Long countAllByTypeAndProvince(@Param("type") Type type, @Param("province") String province, @Param("status") Orders.Status status);
}
