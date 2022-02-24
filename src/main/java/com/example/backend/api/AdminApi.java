package com.example.backend.api;

import com.example.backend.entity.News;
import com.example.backend.entity.Shop;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.model.adminModel.AUserActiveReq;
import com.example.backend.model.newsModel.NewsReq;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.model.userModel.LoginReq;
import com.example.backend.model.userModel.UserEditReq;
import com.example.backend.process.business.*;
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
    private final NewsBusiness newsBusiness;

    public AdminApi(ShopBusiness shopBusiness, TypeBusiness typeBusiness, UserBusiness userBusiness, OrderBusiness orderBusiness, TypeBuyingBusiness buyingBusiness, NewsBusiness newsBusiness) {
        this.shopBusiness = shopBusiness;
        this.typeBusiness = typeBusiness;
        this.userBusiness = userBusiness;
        this.orderBusiness = orderBusiness;
        this.buyingBusiness = buyingBusiness;
        this.newsBusiness = newsBusiness;
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
    public ResponseEntity<Object> updateUserActive(@RequestBody AUserActiveReq req) throws BaseException {
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

    @PostMapping("/user/profileByShop")
    public ResponseEntity<Object> userByShop(@RequestBody Shop req) throws BaseException {
        Object user = userBusiness.userByShop(req);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/save")
    public ResponseEntity<Object> userEditProfile(@RequestBody UserEditReq req) throws BaseException {
        Object res = userBusiness.saveUserById(req);

        return ResponseEntity.ok(res);
    }

    /*   TYPE    */
    @PostMapping("/type/list")
    public ResponseEntity<Object> typeList() {
        Object list = typeBusiness.list();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/type/save")
    public ResponseEntity<Object> typeSave(@RequestBody Type req) throws BaseException {
        Object res = typeBusiness.save(req);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/type/delete")
    public ResponseEntity<Object> typeDelete(@RequestBody Type req) throws BaseException {
        Object res = typeBusiness.delete(req);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/type/recovery")
    public ResponseEntity<Object> typeRecovery(@RequestBody Type req) throws BaseException {
        Object res = typeBusiness.recovery(req);

        return ResponseEntity.ok(res);
    }

    /*   BUYING    */
    @PostMapping("/buying/listByShop")
    public ResponseEntity<Object> listByShop(@RequestBody Shop req) throws BaseException {
        Object listByShop = buyingBusiness.getListByShop(req);

        return ResponseEntity.ok(listByShop);
    }

    /*  NEWS  */
    @PostMapping("/news/delete")
    public ResponseEntity<Object> deleteNews(@RequestBody NewsReq req) throws BaseException {
        newsBusiness.delete(req.getId());

        return ResponseEntity.ok(req);
    }

    @PostMapping("/news/save")
    public ResponseEntity<Object> save(@RequestBody NewsReq req) throws BaseException {
        Object save = newsBusiness.save(req);

        return ResponseEntity.ok(save);
    }

}
