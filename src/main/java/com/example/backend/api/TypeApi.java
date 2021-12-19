package com.example.backend.api;

import com.example.backend.business.TypeBusiness;
import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.model.typeModel.TypeReq;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeApi {

    public final TypeBusiness business;

    public TypeApi(TypeBusiness business) {
        this.business = business;
    }

    @GetMapping("/list")
    public List<Type> list() {

        return business.list();
    }

    @PostMapping("/save")
    public Object save(@RequestBody TypeReq req) throws BaseException {

        Object res = business.save(req);
        return res;
    }


}
