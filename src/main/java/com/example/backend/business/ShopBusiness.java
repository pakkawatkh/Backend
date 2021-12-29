package com.example.backend.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.ShopException;
import com.example.backend.mapper.ShopMapper;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.model.shopModel.ShopResponse;
import com.example.backend.service.ShopService;
import com.example.backend.service.UserService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

@Service
public class ShopBusiness {

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
        Object save = service.saveShop(user, req.getName(), req.getLatitude(), req.getLongitude());

        userService.updateRole(user, User.Role.SHOP);

        return save;
    }

    public Object edit(ShopReq req) throws BaseException {

        User user = tokenService.getUserByToken();

        if (req.getName() == null || req.getLatitude() == null || req.getLongitude() == null) {
            throw ShopException.requestInvalid();
        }

        Object edit = service.edit(user, req.getId(), req.getName(), req.getLatitude(), req.getLongitude());
        return edit;
    }

    public Object changStatus(ShopReq req) throws BaseException {

        User admin = tokenService.getUserByToken();

        if (admin.getRole() != User.Role.ADMIN) {
            throw ShopException.accessDenied();
        }

        if (req.getActive() == null) {
            throw ShopException.requestInvalid();
        }

        Object changStatus = service.changStatus(req.getId(), req.getActive());

        return changStatus;

    }

    public ShopResponse profile() throws BaseException {
        User user = tokenService.getUserByToken();

        if (user.getRole() != User.Role.SHOP) {
            throw ShopException.accessDenied();
        }

        Shop shop = user.getShop();

        if (shop == null) {
            throw ShopException.accessDenied();
        }

        return mapper.toShopResponse(shop);
    }


}
