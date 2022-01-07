package com.example.backend.api;

import com.example.backend.business.*;
import com.example.backend.entity.*;
import com.example.backend.exception.BaseException;
import com.example.backend.model.adminModel.UserActiveReq;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.model.typeModel.TypeReq;
import com.example.backend.model.userModel.LoginReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminApi {
    private final ShopBusiness shopBusiness;
    private final TypeBusiness typeBusiness;
    private final UserBusiness userBusiness;
    private final OrderBusiness orderBusiness;
    private final TypeBuyingBusiness buyingBusiness;

    public AdminApi(ShopBusiness shopBusiness, TypeBusiness typeBusiness, UserBusiness userBusiness, OrderBusiness orderBusiness, TypeBuyingBusiness buyingBusiness) {
        this.shopBusiness = shopBusiness;
        this.typeBusiness = typeBusiness;
        this.userBusiness = userBusiness;
        this.orderBusiness = orderBusiness;
        this.buyingBusiness = buyingBusiness;
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq req) throws BaseException {
        Object res = userBusiness.loginAdmin(req);
        return ResponseEntity.ok(res);
    }

    /*   SHOP    */
    @PostMapping("/shop/active")
    public ResponseEntity<Object> updateStatus(@RequestBody ShopReq req) throws BaseException {

        Object res = shopBusiness.changStatus(req);


        return ResponseEntity.ok(res);

    }

    @PostMapping("/shop/list")
    public ResponseEntity<Object> shopList() throws BaseException {
        Object list = shopBusiness.list();
        return ResponseEntity.ok(list);
    }
    @PostMapping("/shop/profile")
    public ResponseEntity<Object> shopById(@RequestBody ShopReq req) throws BaseException {
        Object list = shopBusiness.byId(req);
        return ResponseEntity.ok(list);
    }

    /*   ORDER    */
    @PostMapping("/order/list")
    public ResponseEntity<Object> orderList() throws BaseException {
        Object orderAllUser = orderBusiness.getOrderAllUser();
        return ResponseEntity.ok(orderAllUser);
    }

    @PostMapping("/order/listByUser")
    public ResponseEntity<Object> orderListByUser(@RequestBody User req) throws BaseException {

        Object orderByUser = orderBusiness.getOrderByUser(req);
        return ResponseEntity.ok(orderByUser);

    }

    /*   USER    */
    @PostMapping("/user/active")
    public ResponseEntity<Object> updateUserActive(@RequestBody UserActiveReq req) throws BaseException {
        Object res = userBusiness.updateUserActive(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/user/list")
    public ResponseEntity<Object> userList() throws BaseException {
        Object users = userBusiness.userList();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/user/profile")
    public ResponseEntity<Object> userById(@RequestBody User req) throws BaseException {

        Object user = userBusiness.userById(req);
        return ResponseEntity.ok(user);
    }

    /*   TYPE    */
    @PostMapping("/type/list")
    public ResponseEntity<Object> typeList() {

        Object list = this.typeBusiness.list();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/type/save")
    public ResponseEntity<Object> save(@RequestBody TypeReq req) throws BaseException {

        Object res = typeBusiness.save(req);

        return ResponseEntity.ok(res);
    }

    /*   BUYING    */
    @PostMapping("/buying/listByShop")
    public ResponseEntity<Object> listByShop(@RequestBody Shop req) throws BaseException {

        Object listByShop = buyingBusiness.getListByShop(req);
        return ResponseEntity.ok(listByShop);
    }
}
