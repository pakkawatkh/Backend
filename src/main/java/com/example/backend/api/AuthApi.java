package com.example.backend.api;

import com.example.backend.exception.BaseException;
import com.example.backend.model.orderModel.OrderBuyFillerReq;
import com.example.backend.model.userModel.LoginReq;
import com.example.backend.model.userModel.RegisterReq;
import com.example.backend.process.business.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    private final UserBusiness userBusiness;
    private final OrderBusiness orderBusiness;
    private final TypeBusiness typeBusiness;
    private final ShopBusiness shopBusiness;
    private final NewsBusiness newsBusiness;

    public AuthApi(UserBusiness userBusiness, OrderBusiness orderBusiness, TypeBusiness typeBusiness, ShopBusiness shopBusiness, NewsBusiness newsBusiness) {
        this.userBusiness = userBusiness;
        this.orderBusiness = orderBusiness;
        this.typeBusiness = typeBusiness;
        this.shopBusiness = shopBusiness;
        this.newsBusiness = newsBusiness;
    }

    //user
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterReq req) throws BaseException {
        Object response = userBusiness.register(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq req) throws BaseException {
        Object res = userBusiness.loginUser(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user-profile/{number}")
    public ResponseEntity<Object> userProfile(@PathVariable("number") String number) throws BaseException {
        Object res = userBusiness.userByNumber(number);
        return ResponseEntity.ok(res);
    }

//    order
    @GetMapping("/order-user/{number}")
    public ResponseEntity<Object> orderByUser(@PathVariable("number") String number) throws BaseException {
        Object res = orderBusiness.orderListByUser(number);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/order-province")
    public ResponseEntity<Object> provinceIsBuy(){
        Object res = orderBusiness.selectProvinceIsBy();
        return ResponseEntity.ok(res);
    }
    @PostMapping("/order-list-all")
    public ResponseEntity<Object> listFilterOrder(@RequestBody OrderBuyFillerReq req) throws BaseException {
        Object res = orderBusiness.getListFilter(req);
        return  ResponseEntity.ok(res);
    }
    @GetMapping("/order-random")
    public ResponseEntity<Object> randomOrder(){
        Object res = orderBusiness.random();
        return ResponseEntity.ok(res);
    }

    //type
    @GetMapping("/type-list")
    public ResponseEntity<Object> listType() {
        Object list = this.typeBusiness.listActive();
        return ResponseEntity.ok(list);
    }

    //NEWS
    @GetMapping("/news-list")
    public ResponseEntity<Object> listNews(){
        Object res = newsBusiness.getList();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/news-detail/{id}")
    public ResponseEntity<Object> detailNews(@PathVariable("id") Integer id) throws BaseException {
        Object res = newsBusiness.getDetailById(id);

        return ResponseEntity.ok(res);
    }
    @GetMapping("/news-recommend/{id}")
    public ResponseEntity<Object> recommendNews(@PathVariable(value = "id",required = false) Integer id){
        Object res = newsBusiness.getRecommend(id);
        return ResponseEntity.ok(res);
    }

    //SHOP
    @GetMapping("/shop-list")
    public ResponseEntity<Object> listShop() throws BaseException {
        Object list = shopBusiness.listActive();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/shop-byId/{id}")
    public ResponseEntity<Object> byIdShop(@PathVariable("id") Integer id) throws BaseException {
        Object shopResponse = shopBusiness.byIdActive(id);
        return ResponseEntity.ok(shopResponse);
    }
}
