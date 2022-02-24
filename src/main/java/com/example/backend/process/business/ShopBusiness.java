package com.example.backend.process.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.mapper.ShopMapper;
import com.example.backend.model.Response;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.model.shopModel.ShopResponse;
import com.example.backend.process.service.ShopService;
import com.example.backend.process.service.UserService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ShopBusiness {
    private final ShopService service;
    private final TokenService tokenService;
    private final ShopMapper mapper;
    private final UserService userService;
    private String MS = "OK";

    public ShopBusiness(ShopService service, TokenService tokenService, ShopMapper mapper, UserService userService) {
        this.service = service;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.userService = userService;
    }

    public Object register(ShopReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (req.getName().isBlank() || Objects.isNull(req.getLatitude()) || Objects.isNull(req.getLongitude()))
            throw MainException.requestInvalid();

        service.existsByUser(user);
        service.existsByName(req.getName());
        service.saveShop(user, req.getName(), req.getLatitude(), req.getLongitude());
        userService.updateRole(user, User.Role.SHOP);

        return new Response().success("create success");

    }

    public Object edit(ShopReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (req.getName().isBlank() || Objects.isNull(req.getLatitude()) || Objects.isNull(req.getLongitude()))
            throw MainException.requestInvalid();
        service.edit(user, req.getId(), req.getName(), req.getLatitude(), req.getLongitude());

        return new Response().success("edit success");
    }

    public Object changStatus(ShopReq req) throws BaseException {
        tokenService.checkAdminByToken();
        if (Objects.isNull(req.getActive()) || Objects.isNull(req.getId())) throw MainException.requestInvalid();

        Shop shop = service.findById(req.getId());
        service.changStatus(req.getId(), req.getActive());

        User.Role user;
        if (req.getActive()) user = User.Role.SHOP;
        else user = User.Role.USER;
        userService.updateRole(shop.getUser(), user);

        return new Response().success("chang status success");

    }

    public Object profile() throws BaseException {
        User user = tokenService.getUserByToken();
        if (user.getRole() != User.Role.SHOP) throw MainException.accessDenied();

        Shop shop = user.getShop();
        if (Objects.isNull(shop)) throw MainException.accessDenied();

        ShopResponse shopResponse = mapper.toShopResponse(shop);

        return new Response().ok(MS, "profile", shopResponse);
    }

    public Object listActive() throws BaseException {
        tokenService.getUserByToken();
        List<ShopResponse> shopResponses = mapper.toListShopRes(service.findAllByActive());

        return new Response().ok(MS, "shop", shopResponses);
    }

    public Object list() throws BaseException {
        tokenService.getUserByToken();
        List<ShopResponse> shopResponses = mapper.toListShopRes(service.findAll());

        return new Response().ok(MS, "shop", shopResponses);
    }

    public Object byIdActive(ShopReq req) throws BaseException {
        tokenService.getUserByToken();
        ShopResponse shopResponse = mapper.toShopResponse(service.findByIdAndActive(req.getId()));

        return new Response().ok(MS, "profile", shopResponse);
    }

    public Object byId(ShopReq req) throws BaseException {
        tokenService.getUserByToken();
        ShopResponse shopResponse = mapper.toShopResponse(service.findById(req.getId()));

        return new Response().ok(MS, "profile", shopResponse);
    }

}
