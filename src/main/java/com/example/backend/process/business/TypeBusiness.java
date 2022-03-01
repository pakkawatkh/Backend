package com.example.backend.process.business;

import com.example.backend.entity.Type;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.model.Response;
import com.example.backend.model.typeModel.TypeReq;
import com.example.backend.process.service.OrderService;
import com.example.backend.process.service.TypeService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TypeBusiness {
    private final TypeService service;
    private final TokenService tokenService;
    private final OrderService orderService;
    private final String MS = "OK";

    public TypeBusiness(TypeService service, TokenService tokenService, OrderService orderService) {
        this.service = service;
        this.tokenService = tokenService;
        this.orderService = orderService;
    }

    public Object list() throws BaseException {
        tokenService.checkAdminByToken();
        List<Type> list = service.findAll();

        return new Response().ok(MS, "type", list);
    }

    public Object listActive() {
        List<Type> list = service.findByActive();

        return new Response().ok(MS, "type", list);
    }

    public Object save(TypeReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (Objects.isNull(req.getName())) throw MainException.requestInvalid();
        if (req.getName().isBlank()) throw MainException.requestIsBlank();

        service.create(req.getName());

        return new Response().success("create success");
    }

    public Object edit(Integer id, TypeReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (Objects.isNull(req.getName())) throw MainException.requestInvalid();
        if (req.getName().isBlank()) throw MainException.requestIsBlank();

        service.edit(id, req.getName());

        return new Response().success("edit success");
    }

    public Object delete(Integer id) throws BaseException {
        tokenService.checkAdminByToken();
        String ms;

        Type type = service.findById(id);
        boolean check = orderService.existsAllByType(type);
        if (check) {
            service.changStatusById(type, false);
            ms = "Cannot be deleted, but will be hidden";
        } else {
            service.deleteById(id);
            ms = "delete success";
        }

        return new Response().success(ms);
    }

    public Object recovery(Integer id) throws BaseException {
        tokenService.checkAdminByToken();

        Type type = service.findById(id);
        service.changStatusById(type, true);

        return new Response().success("recover success");
    }
}
