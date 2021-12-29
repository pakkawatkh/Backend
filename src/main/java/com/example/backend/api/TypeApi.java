package com.example.backend.api;

import com.example.backend.business.TypeBusiness;
import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.model.typeModel.TypeReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeApi {

    public final TypeBusiness business;

    public TypeApi(TypeBusiness business) {
        this.business = business;
    }

    @PostMapping("/list")
    public ResponseEntity<List<Type>> list() {

        List<Type> list = this.business.list();

        return ResponseEntity.ok(list);
    }

}
