package com.example.backend.business;

import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.TypeException;
import com.example.backend.model.Response;
import com.example.backend.service.OrderService;
import com.example.backend.service.TypeService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TypeBusiness {
    private final TypeService service;
    private final TokenService tokenService;
    private final OrderService orderService;
    private String MS = "OK";

    public TypeBusiness(TypeService service, TokenService tokenService, OrderService orderService) {
        this.service = service;
        this.tokenService = tokenService;
        this.orderService = orderService;
    }

    public Object list() {

        List<Type> list = service.findAll();
        return new Response().ok(MS, "type", list);
    }
    public Object listActive() {

        List<Type> list = service.findByActive();
        return new Response().ok(MS, "type", list);
    }

    public Object save(Type req) throws BaseException {
        String mss;
        tokenService.checkAdminByToken();

        if (Objects.isNull(req.getName())||req.getName().contains(" ")){
            throw TypeException.requestInvalid();
        }

        if ( Objects.isNull(req.getId())) {
            service.create(req.getName());
            mss = "create";
        } else {
            service.edit(req.getId(), req.getName());
            mss = "edit";
        }
        return new Response().success(mss + " success");
    }

    public Object delete(Type req) throws BaseException {
        String ms;
        Type type = service.findById(req.getId());
        boolean check = orderService.fineByType(type);
        if (check){
            service.changStatusById(type,false);
            ms = "Cannot be deleted, but will be hidden";
        }
        else {
            service.deleteById(req.getId());
            ms = "delete success";
        }
        return new Response().success(ms);
    }
    public Object recovery(Type req) throws BaseException {

        Type type = service.findById(req.getId());

        service.changStatusById(type,true);

        return new Response().success("recover success");
    }
}
