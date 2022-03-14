package com.example.backend.api;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.model.Response;
import com.example.backend.model.orderModel.OrderBuyFillerReq;
import com.example.backend.model.userModel.LoginReq;
import com.example.backend.model.userModel.RegisterReq;
import com.example.backend.process.business.*;
import com.example.backend.process.service.token.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    private final OrderBusiness orderBusiness;
    private final TypeBusiness typeBusiness;
    private final ShopBusiness shopBusiness;
    private final NewsBusiness newsBusiness;
    private final TypeBuyingBusiness typeBuyingBusiness;

    public AuthApi(OrderBusiness orderBusiness, TypeBusiness typeBusiness, ShopBusiness shopBusiness, NewsBusiness newsBusiness, TypeBuyingBusiness typeBuyingBusiness) {
        this.orderBusiness = orderBusiness;
        this.typeBusiness = typeBusiness;
        this.shopBusiness = shopBusiness;
        this.newsBusiness = newsBusiness;
        this.typeBuyingBusiness = typeBuyingBusiness;
    }

    //    order
    @GetMapping("/order-user/{number}")
    public ResponseEntity<Object> orderByUser(@PathVariable("number") String number) throws BaseException {
        Object res = orderBusiness.orderListByUser(number);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/order-province")
    public ResponseEntity<Object> provinceIsBuy() {
        Object res = orderBusiness.selectProvinceIsBy();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/order-list-all")
    public ResponseEntity<Object> listFilterOrder(@RequestBody OrderBuyFillerReq req) throws BaseException {
        Object res = orderBusiness.getListFilter(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/order-random")
    public ResponseEntity<Object> randomOrder() {
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
    public ResponseEntity<Object> listNews() {
        Object res = newsBusiness.getList();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/news-detail/{id}")
    public ResponseEntity<Object> detailNews(@PathVariable("id") Integer id) throws BaseException {
        Object res = newsBusiness.getDetailById(id);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/news-recommend/{id}")
    public ResponseEntity<Object> recommendNews(@PathVariable(value = "id", required = false) Integer id) {
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

    //typeBuying

    @GetMapping("/buying/byShop/{id}")
    public ResponseEntity<Object> getBuyingByShop(@PathVariable("id") Integer id) throws BaseException {
        Object res = typeBuyingBusiness.getAllByShop(id);
        return ResponseEntity.ok(res);
    }
}

@RestController
@RequestMapping("/auth")
class AuthUser {

    private final UserBusiness business;
    private final TokenService tokenService;
    @Value("${app.login-social.user-id}")
    private String userId;

    AuthUser(UserBusiness business, TokenService tokenService) {
        this.business = business;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterReq req) throws BaseException {
        Object response = business.register(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginReq req) throws BaseException {
        Object res = business.loginUser(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user-profile/{number}")
    public ResponseEntity<Object> userProfile(@PathVariable("number") String number) throws BaseException {
        Object res = business.userByNumber(number);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/loginSocial/getToken")
    public Object token() {
        User user = new User();
        user.setId(this.userId);

        return new Response().ok("ok", "token", tokenService.tokenizeSocial(user));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<Object> loginAdmin(@RequestBody LoginReq req) throws BaseException {
        Object res = business.loginAdmin(req);

        return ResponseEntity.ok(res);
    }
}
