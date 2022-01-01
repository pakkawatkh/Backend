package com.example.backend.business;

import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.model.Response;
import com.example.backend.model.typeModel.TypeReq;
import com.example.backend.service.TypeService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeBusiness {
    private final TypeService service;
    private final TokenService tokenService;
    private String MS = "OK";

    public TypeBusiness(TypeService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    public Object list() {

        List<Type> list = service.list();
        return new Response().ok(MS, "type", list);
    }

    public Object save(TypeReq req) throws BaseException {
        String mss;
        tokenService.checkAdminByToken();

        Object res;
        if (req.getId() == null) {
            service.create(req.getName());
            mss = "create";
        } else {
            service.edit(req.getId(), req.getName());
            mss = "edit";
        }
        return new Response().success(mss + " success");

    }
}
