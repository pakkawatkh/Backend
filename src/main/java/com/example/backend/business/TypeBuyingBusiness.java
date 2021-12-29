package com.example.backend.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.TypeBuyingException;
import com.example.backend.model.TypeBuyingModel.BuyingReq;
import com.example.backend.service.TypeBuyingService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TypeBuyingBusiness {

    public final TokenService tokenService;
    public final TypeBuyingService service;

    public TypeBuyingBusiness(TokenService tokenService, TypeBuyingService service) {
        this.tokenService = tokenService;
        this.service = service;
    }


    public Object create(BuyingReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (user.getRole() != User.Role.SHOP) {
            throw TypeBuyingException.notAllowed();
        }

        if (user.getShop() == null) {
            throw TypeBuyingException.notAllowed();
        }

        Shop shop = user.getShop();
        if (shop.getActive() == false) {
            throw TypeBuyingException.accessDenied();
        }

        Object save = service.saveBuying(shop, req.getName(), req.getPrice());

        return save;
    }
}
