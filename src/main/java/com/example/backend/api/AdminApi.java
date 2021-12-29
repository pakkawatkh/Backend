package com.example.backend.api;

import com.example.backend.business.ShopBusiness;
import com.example.backend.business.TypeBusiness;
import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.model.typeModel.TypeReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminApi {
    private final ShopBusiness shopBusiness;
    private final TypeBusiness typeBusiness;

    public AdminApi(ShopBusiness shopBusiness, TypeBusiness typeBusiness) {
        this.shopBusiness = shopBusiness;
        this.typeBusiness = typeBusiness;
    }
/*   SHOP    */
    @PostMapping("/updateStatus")
    public ResponseEntity<Object> updateStatus(@RequestBody ShopReq req) throws BaseException {

        Object res = shopBusiness.changStatus(req);

        return ResponseEntity.ok(res);

    }

    /*   ORDER    */
    /*   USER    */



    /*   TYPE    */
    @PostMapping("/list")
    public ResponseEntity<List<Type>> list() {

        List<Type> list = this.typeBusiness.list();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody TypeReq req) throws BaseException {

        Object res = typeBusiness.save(req);

        return ResponseEntity.ok(res);
    }

    /*   BUYING    */

}
