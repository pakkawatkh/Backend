package com.example.backend.process.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.TypeBuying;
import com.example.backend.entity.TypeBuyingList;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.model.Response;
import com.example.backend.model.TypeBuyingModel.BuyingListReq;
import com.example.backend.model.TypeBuyingModel.BuyingReq;
import com.example.backend.process.service.ShopService;
import com.example.backend.process.service.TypeBuyingService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TypeBuyingBusiness {
    public final TokenService tokenService;
    public final TypeBuyingService service;
    public final ShopService shopService;
    private final String MS = "OK";

    public TypeBuyingBusiness(TokenService tokenService, TypeBuyingService service, ShopService shopService) {
        this.tokenService = tokenService;
        this.service = service;
        this.shopService = shopService;
    }

    public Object create(BuyingReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (user.getRole() != User.Role.SHOP) throw MainException.accessDenied();
        if (Objects.isNull(user.getShop())) throw MainException.accessDenied();

        Shop shop = user.getShop();
        if (!shop.getActive()) throw MainException.accessDenied();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.saveBuying(shop, req.getName());

        return new Response().success("create success");
    }

    public Object getListByShop(Integer shopId) throws BaseException {


        Shop shop = shopService.findById(shopId);
        List<TypeBuying> buying = service.findByShop(shop);

        return new Response().ok(MS, "buying", buying);
    }

    public Object createChild(BuyingListReq req) throws BaseException {

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        TypeBuying typeBuying = service.findById(req.getBuyingId());
        service.saveChild(typeBuying, req.getName(), req.getPrice());
        return new Response().success("create success");
    }
}
