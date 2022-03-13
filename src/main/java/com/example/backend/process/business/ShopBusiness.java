package com.example.backend.process.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.mapper.ShopMapper;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.model.Response;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.model.shopModel.ShopResponse;
import com.example.backend.model.userModel.UserInOrderResponse;
import com.example.backend.process.service.ShopService;
import com.example.backend.process.service.TypeBuyingService;
import com.example.backend.process.service.UserService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ShopBusiness {
    private final ShopService service;
    private final TypeBuyingService typeBuyingService;
    private final TokenService tokenService;
    private final ShopMapper mapper;
    private final UserService userService;
    private String MS = "OK";

    public ShopBusiness(ShopService service, TypeBuyingService typeBuyingService, TokenService tokenService, ShopMapper mapper, UserService userService) {
        this.service = service;
        this.typeBuyingService = typeBuyingService;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.userService = userService;
    }

    public Object register(ShopReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.existsByUser(user);
        service.existsByName(req.getName());
        service.saveShop(user, req.getName(), req.getLatitude(), req.getLongitude());
        userService.updateRole(user, User.Role.SHOP);

        return new Response().success("create success");

    }

    public Object edit(ShopReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.edit(user, req.getName(), req.getLatitude(), req.getLongitude());

        return new Response().success("edit success");
    }

    public Object changStatus(Integer id, ShopReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid2()) throw MainException.requestInvalid();

        Shop shop = service.findById(id);
        service.changStatus(shop, req.getActive());

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

    public Object listActive() {
        List<ShopResponse> shopResponses = mapper.toListShopRes(service.findAllByActive());
        shopResponses = this.updateListPictureUser(shopResponses);
        return new Response().ok(MS, "shop", shopResponses);
    }

    public Object list() throws BaseException {
        tokenService.checkAdminByToken();
        List<ShopResponse> shopResponses = mapper.toListShopRes(service.findAll());

        return new Response().ok(MS, "shop", shopResponses);
    }

    public Object byIdActive(Integer id) throws BaseException {
        Shop shop = service.findByIdAndActive(id);
        ShopResponse shopResponse = mapper.toShopResponse(shop);
        shopResponse = this.updatePictureUser(shopResponse);
        Long count = typeBuyingService.countByShop(shop);
        shopResponse.setCount(count);
        return new Response().ok(MS, "profile", shopResponse);
    }

    public Object byId(Integer id) throws BaseException {
        tokenService.checkAdminByToken();
        ShopResponse shopResponse = mapper.toShopResponse(service.findById(id));

        return new Response().ok(MS, "profile", shopResponse);
    }

    public ShopResponse updatePictureUser(ShopResponse shopResponse) {
        UserInOrderResponse user = shopResponse.getUser();
        if (user.getPicture() != null) {
            BaseUrlFile url = new BaseUrlFile();
            user.setPicture(url.getDomain() + url.getImageProfileUrl() + user.getPicture());
            shopResponse.setUser(user);
        }
        return shopResponse;
    }

    public List<ShopResponse> updateListPictureUser(List<ShopResponse> shopResponses) {
        BaseUrlFile url = new BaseUrlFile();
        for (ShopResponse response : shopResponses) {
            UserInOrderResponse user = response.getUser();
            if (user.getPicture() != null) {
                user.setPicture(url.getDomain() + url.getImageProfileUrl() + user.getPicture());
                response.setUser(user);
            }
        }
        return shopResponses;
    }

}
