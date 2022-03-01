package com.example.backend.api;

import com.example.backend.process.business.ShopBusiness;
import com.example.backend.exception.BaseException;
import com.example.backend.model.shopModel.ShopReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopApi {
    private final ShopBusiness business;

    public ShopApi(ShopBusiness business) {
        this.business = business;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody ShopReq req) throws BaseException {
        Object res = business.register(req);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/edit")
    public ResponseEntity<Object> edit(@RequestBody ShopReq req) throws BaseException {
        Object res = business.edit(req);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> profile() throws BaseException {
        Object profile = business.profile();

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list() throws BaseException {
        Object list = business.listActive();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/byId")
    public ResponseEntity<Object> byId(@RequestBody ShopReq req) throws BaseException {
        Object shopResponse = business.byIdActive(req);

        return ResponseEntity.ok(shopResponse);
    }
}
