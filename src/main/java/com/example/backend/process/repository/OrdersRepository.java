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

    @Query(value = "SELECT * FROM Orders WHERE Orders.status =:status AND Orders.id !=:id ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Orders> recommendByStatusLimitNotIdAndType(@Param("id") Integer id, @Param("status") String status, @Param("limit") Integer limit);

    @Query(value = "SELECT o.picture FROM Orders as o")
    String[] getAllPicture();

    @Query(value = "SELECT o FROM Orders as o WHERE o.status =:status AND (o.name LIKE %:name% OR o.type.name LIKE %:name%)")
    List<Orders> getSearchName(@Param("name") String name, @Param("status") Orders.Status status);

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

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o where  o.status = :status GROUP BY o.type ORDER BY o.type.id asc ")
    List<Object> getAllType(@Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o where o.status = :status GROUP BY o.province ORDER BY o.province asc ")
    List<Object> getAllProvince(@Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where  o.status = :status")
    Long countAllBuy(@Param("status") Orders.Status status);


    //--------------type not null, province null, --------------//

    List<Orders> findAllByStatusAndTypeOrderByDateDesc(Orders.Status status, Type type, Pageable pageable);

    List<Orders> findAllByStatusAndTypeOrderByDateAsc(Orders.Status status, Type type, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o where  o.status = :status and o.type =:type GROUP BY o.type")
    List<Object> getByType(@Param("type") Type type, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o where o.status = :status and o.type =:type GROUP BY o.province ORDER BY o.province asc ")
    List<Object> getAllProvinceByType(@Param("type") Type type, @Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where o.type = :type and o.status = :status")
    Long countAllByType(@Param("type") Type type, @Param("status") Orders.Status status);


    //--------------type null, province not null, district null--------------//

    List<Orders> findAllByStatusAndProvinceOrderByDateDesc(Orders.Status status, String province, Pageable pageable);

    List<Orders> findAllByStatusAndProvinceOrderByDateAsc(Orders.Status status, String province, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o where  o.status = :status and o.province = :province GROUP BY o.type ORDER BY o.type.id asc ")
    List<Object> getAllTypeByProvince(@Param("province") String province, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o where o.status = :status and o.province = :province GROUP BY o.province")
    List<Object> getByProvince(@Param("province") String province, @Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where o.province = :province and o.status = :status")
    Long countAllByProvince(@Param("province") String province, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.district ,count(o) FROM Orders as o where o.status = :status and o.province = :province GROUP BY o.district")
    List<Object> getDistrictByProvince(@Param("province") String province, @Param("status") Orders.Status status);

    //--------------type null, province not null,  district not null--------------//
    List<Orders> findAllByStatusAndProvinceAndDistrictOrderByDateDesc(Orders.Status status, String province,String district, Pageable pageable);

    List<Orders> findAllByStatusAndProvinceAndDistrictOrderByDateAsc(Orders.Status status, String province,String district, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o where  o.status = :status and o.province = :province and o.district =:district GROUP BY o.type ORDER BY o.type.id asc ")
    List<Object> getAllTypeByProvinceAndDistrict(@Param("province") String province, @Param("district") String district, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o where o.status = :status and o.province = :province and o.district =:district GROUP BY o.province")
    List<Object> getByProvinceAndDistrict(@Param("province") String province, @Param("district") String district, @Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where o.province = :province and o.status = :status and o.district =:district")
    Long countAllByProvinceAndDistrict(@Param("province") String province, @Param("district") String district, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.district ,count(o) FROM Orders as o where o.status = :status and o.province = :province and o.district =:district GROUP BY o.district")
    List<Object> getDistrictByProvinceAndDistrict(@Param("province") String province, @Param("district") String district, @Param("status") Orders.Status status);

    //-------------type not null, province not null,  district null---------------//

    List<Orders> findAllByStatusAndTypeAndProvinceOrderByDateDesc(Orders.Status status, Type type, String province, Pageable pageable);

    List<Orders> findAllByStatusAndTypeAndProvinceOrderByDateAsc(Orders.Status status, Type type, String province, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o  where o.province = :province and o.type = :type and o.status = :status GROUP BY o.type")
    List<Object> getTypeByTypeAndProvince(@Param("type") Type type, @Param("province") String province, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o  where o.province = :province and o.type = :type and o.status = :status GROUP BY o.province")
    List<Object> getProvinceByTypeAndProvince(@Param("type") Type type, @Param("province") String province, @Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where o.province = :province and o.type = :type and o.status = :status")
    Long countAllByTypeAndProvince(@Param("type") Type type, @Param("province") String province, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.district ,count(o) FROM Orders as o where o.status = :status and o.province = :province and o.type = :type GROUP BY o.district")
    List<Object> getDistrictByProvinceAndType(@Param("type") Type type,@Param("province") String province ,@Param("status") Orders.Status status);

    //-------------type not null, province not null,  district not null---------------//

    List<Orders> findAllByStatusAndTypeAndProvinceAndDistrictOrderByDateDesc(Orders.Status status, Type type, String province,String district, Pageable pageable);

    List<Orders> findAllByStatusAndTypeAndProvinceAndDistrictOrderByDateAsc(Orders.Status status, Type type, String province,String district, Pageable pageable);

    @Query(value = "SELECT o.type ,count(o) FROM Orders as o  where o.province = :province and o.district =:district  and o.type = :type and o.status = :status GROUP BY o.type")
    List<Object> getTypeByTypeAndProvinceAndDistrict(@Param("type") Type type, @Param("province") String province, @Param("district") String district, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.province ,count(o) FROM Orders as o  where o.province = :province and o.district =:district and o.type = :type and o.status = :status GROUP BY o.province")
    List<Object> getProvinceByTypeAndProvinceAndDistrict(@Param("type") Type type, @Param("province") String province, @Param("district") String district, @Param("status") Orders.Status status);

    @Query(value = "SELECT count(o) FROM Orders as o where o.province = :province and o.district =:district and o.type = :type and o.status = :status")
    Long countAllByTypeAndProvinceAndDistrict(@Param("type") Type type, @Param("province") String province, @Param("district") String district, @Param("status") Orders.Status status);

    @Query(value = "SELECT o.district ,count(o) FROM Orders as o where o.status = :status and o.province = :province and o.district =:district and o.type = :type GROUP BY o.district")
    List<Object> getDistrictByProvinceAndDistrictAndType(@Param("type") Type type,@Param("province") String province, @Param("district") String district, @Param("status") Orders.Status status);
}
