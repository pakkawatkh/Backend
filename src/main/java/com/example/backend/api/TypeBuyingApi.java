package com.example.backend.api;

import com.example.backend.entity.TypeBuyingList;
import com.example.backend.model.TypeBuyingModel.BuyingListReq;
import com.example.backend.process.business.TypeBuyingBusiness;
import com.example.backend.entity.Shop;
import com.example.backend.exception.BaseException;
import com.example.backend.model.TypeBuyingModel.BuyingReq;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("list/byShop/{id}")
    public ResponseEntity<Object> listByShop(@PathVariable("id") Integer id) throws BaseException {
        Object listByShop = business.getListByShop(id);

        return ResponseEntity.ok(listByShop);
    }

    //Buying List
    @PostMapping("/child/create")
    public ResponseEntity<Object> childCreate(@RequestBody BuyingListReq req) throws BaseException {
        Object res = business.createChild(req);

        return ResponseEntity.ok(res);
    }


}
