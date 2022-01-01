package com.example.backend.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.ShopException;
import com.example.backend.mapper.ShopMapper;
import com.example.backend.model.Response;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.model.shopModel.ShopResponse;
import com.example.backend.service.ShopService;
import com.example.backend.service.UserService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopBusiness {
    private String MS = "OK";

    private final ShopService service;
    private final TokenService tokenService;
    private final ShopMapper mapper;
    private final UserService userService;

    public ShopBusiness(ShopService service, TokenService tokenService, ShopMapper mapper, UserService userService) {
        this.service = service;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.userService = userService;
    }

    public Object register(ShopReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (req.getName() == null || req.getLatitude() == null || req.getLongitude() == null) {
            throw ShopException.requestInvalid();
        }

        service.existsByUser(user);

        service.existsByName(req.getName());

        service.saveShop(user, req.getName(), req.getLatitude(), req.getLongitude());

        userService.updateRole(user, User.Role.SHOP);

        return new Response().success("create success");

    }

    public Object edit(ShopReq req) throws BaseException {

        User user = tokenService.getUserByToken();

        if (req.getName() == null || req.getLatitude() == null || req.getLongitude() == null) {
            throw ShopException.requestInvalid();
        }

        service.edit(user, req.getId(), req.getName(), req.getLatitude(), req.getLongitude());
        return new Response().success("edit success");

    }

    public Object changStatus(ShopReq req) throws BaseException {

        tokenService.checkAdminByToken();

        if (req.getActive() == null) {
            throw ShopException.requestInvalid();
        }

        service.changStatus(req.getId(), req.getActive());

        return new Response().success("chang status success");


    }

    public Object profile() throws BaseException {
        User user = tokenService.getUserByToken();

        if (user.getRole() != User.Role.SHOP) {
            throw ShopException.accessDenied();
        }

        Shop shop = user.getShop();

        if (shop == null) {
            throw ShopException.accessDenied();
        }

        ShopResponse shopResponse = mapper.toShopResponse(shop);
        return new Response().ok(MS, "profile", shopResponse);
    }

    public Object list() throws BaseException {

        tokenService.getUserByToken();
        List<Shop> all = service.findAllByActive();

        return new Response().ok(MS,"shop",all);
    }

    public Object byId(ShopReq req) throws BaseException {

        tokenService.getUserByToken();

        Shop shop = service.findByIdAndActive(req.getId());

        ShopResponse shopResponse = mapper.toShopResponse(shop);
        return new Response().ok(MS, "profile", shopResponse);

    }

}
