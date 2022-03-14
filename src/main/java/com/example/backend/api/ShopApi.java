package com.example.backend.api;

import com.example.backend.model.TypeBuyingModel.BuyingListReq;
import com.example.backend.model.TypeBuyingModel.BuyingReq;
import com.example.backend.process.business.ShopBusiness;
import com.example.backend.exception.BaseException;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.process.business.TypeBuyingBusiness;
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

    @PutMapping("/edit")
    public ResponseEntity<Object> edit(@RequestBody ShopReq req) throws BaseException {
        Object res = business.edit(req);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> profile() throws BaseException {
        Object profile = business.profile();

        return ResponseEntity.ok(profile);
    }
}

@RestController
@RequestMapping("/buying")
class TypeBuyingApi {

    private final TypeBuyingBusiness business;

    public TypeBuyingApi(TypeBuyingBusiness business) {
        this.business = business;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody BuyingReq req) throws BaseException {
        Object res = business.create(req);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") Integer id, @RequestBody BuyingReq req) throws BaseException {
        Object res = business.edit(id, req);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) throws BaseException {
        Object res = business.deleteById(id);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<Object> list(@PathVariable("page") Integer page) throws BaseException {
        Object res = business.listByShopAndPage(page);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/listAll")
    public ResponseEntity<Object> list() throws BaseException {
        Object res = business.listByShop();

        return ResponseEntity.ok(res);
    }

    @GetMapping("list/byShop/{id}")
    public ResponseEntity<Object> listByShop(@PathVariable("id") Integer id) throws BaseException {
        Object listByShop = business.getListByShopId(id);

        return ResponseEntity.ok(listByShop);
    }
}

@RestController
@RequestMapping("/buying/child")
class TypeBuyingChild{
    private final TypeBuyingBusiness business;

    TypeBuyingChild(TypeBuyingBusiness business) {
        this.business = business;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> childCreate(@RequestBody BuyingListReq req) throws BaseException {
        Object res = business.createChild(req);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete/{buying}/{id}")
    public ResponseEntity<Object> childDelete(@PathVariable("buying") Integer buying, @PathVariable("id") Integer id) throws BaseException {
        Object res = business.deleteChild(buying, id);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> childEdit(@PathVariable("id") Integer id, @RequestBody BuyingListReq req) throws BaseException {
        Object res = business.editChild(id, req);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Object> childByBuying(@PathVariable("id") Integer id) throws BaseException {
        Object res = business.childByBuying(id);
        return ResponseEntity.ok(res);
    }
}
