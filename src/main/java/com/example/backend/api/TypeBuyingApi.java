package com.example.backend.api;

import com.example.backend.process.business.TypeBuyingBusiness;
import com.example.backend.entity.Shop;
import com.example.backend.exception.BaseException;
import com.example.backend.model.TypeBuyingModel.BuyingReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buying")
public class TypeBuyingApi {

    private final TypeBuyingBusiness business;

    public TypeBuyingApi(TypeBuyingBusiness business) {
        this.business = business;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody BuyingReq req) throws BaseException {
        Object res = business.create(req);

        return ResponseEntity.ok(res);
    }

    @PostMapping("list/byShop")
    public ResponseEntity<Object> listByShop(@RequestBody Shop req) throws BaseException {
        Object listByShop = business.getListByShop(req);

        return ResponseEntity.ok(listByShop);
    }
}
