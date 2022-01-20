package com.example.backend.api;

import com.example.backend.business.TypeBusiness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/type")
public class TypeApi {

    public final TypeBusiness business;

    public TypeApi(TypeBusiness business) {
        this.business = business;
    }

    @PostMapping("/list")
    public ResponseEntity<Object> list() {

        Object list = this.business.list();

        return ResponseEntity.ok(list);
    }






}
