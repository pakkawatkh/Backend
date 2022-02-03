package com.example.backend.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.TypeBuying;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.TypeBuyingException;
import com.example.backend.model.Response;
import com.example.backend.model.TypeBuyingModel.BuyingReq;
import com.example.backend.service.ShopService;
import com.example.backend.service.TypeBuyingService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeBuyingBusiness {
    public final TokenService tokenService;
    public final TypeBuyingService service;
    public final ShopService shopService;
    private String MS = "OK";

    public TypeBuyingBusiness(TokenService tokenService, TypeBuyingService service, ShopService shopService) {
        this.tokenService = tokenService;
        this.service = service;
        this.shopService = shopService;
    }


    public Object create(BuyingReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (user.getRole() != User.Role.SHOP) {
            throw TypeBuyingException.accessDenied();
        }

        if (user.getShop() == null) {
            throw TypeBuyingException.accessDenied();
        }

        Shop shop = user.getShop();
        if (!shop.getActive()) {
            throw TypeBuyingException.accessDenied();
        }

        if (req.getName() == null) {
            throw TypeBuyingException.requestInvalid();
        }

        service.saveBuying(shop, req.getName());
        return new Response().success("create success");

    }

    public Object getListByShop(Shop req) throws BaseException {
        Shop shop = shopService.findById(req.getId());

        List<TypeBuying> buying = service.findByShop(shop);
        return new Response().ok(MS, "buying", buying);
    }
}
