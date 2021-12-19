package com.example.backend.business;

import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.model.typeModel.TypeReq;
import com.example.backend.service.TypeService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeBusiness {
    private final TypeService service;
    private final TokenService tokenService;

    public TypeBusiness(TypeService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    public List<Type> list() {
        return service.list();
    }

    public Object save(TypeReq req) throws BaseException {
//        User user = tokenService.getUserByToken();
//
//        if (user.getRole()!= User.Role.ADMIN){
//            throw TypeException.noAccess();
//        }
        Object res;
        if (req.getId() == null) {
            res = service.create(req.getName());
        } else {
            res = service.edit(req.getId(), req.getName());
        }
        return res;
    }
}
