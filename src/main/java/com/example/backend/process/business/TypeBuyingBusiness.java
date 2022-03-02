package com.example.backend.process.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.TypeBuying;
import com.example.backend.entity.TypeBuyingList;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.mapper.BuyingMapper;
import com.example.backend.model.Response;
import com.example.backend.model.TypeBuyingModel.BuyingListReq;
import com.example.backend.model.TypeBuyingModel.BuyingListResponse;
import com.example.backend.model.TypeBuyingModel.BuyingReq;
import com.example.backend.model.TypeBuyingModel.BuyingResponse;
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
    private final BuyingMapper mapper;
    private final String MS = "OK";

    public TypeBuyingBusiness(TokenService tokenService, TypeBuyingService service, ShopService shopService, BuyingMapper mapper) {
        this.tokenService = tokenService;
        this.service = service;
        this.shopService = shopService;
        this.mapper = mapper;
    }

    public Object create(BuyingReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (user.getRole() != User.Role.SHOP) throw MainException.accessDenied();
        if (Objects.isNull(user.getShop())) throw MainException.accessDenied();

        Shop shop = user.getShop();
        if (!shop.getActive()) throw MainException.accessDenied();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.saveCreate(shop, req.getName());

        return new Response().success("create success");
    }

    public Object edit(Integer id ,BuyingReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (!user.getRole().equals(User.Role.SHOP)) throw MainException.accessDenied();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        TypeBuying buying = service.findByIdAndShop(id, user.getShop());

        service.saveEdit(buying, req.getName());
        return new Response().success("edit success");

    }
    public Object deleteById(Integer id) throws BaseException {
        User user = tokenService.getUserByToken();

        if (!user.getRole().equals(User.Role.SHOP))throw MainException.accessDenied();

        service.deleteById(id,user.getShop());

        return new Response().success("delete success");
    }

    public Object getListByShopId(Integer shopId) throws BaseException {

        Shop shop = shopService.findById(shopId);
        List<TypeBuying> buying = service.findByShop(shop);

        List<BuyingResponse> responses = mapper.toBuyingResponse(buying);

        return new Response().ok(MS, "buying", responses);
    }
    public Object listByShopAndPage(Integer page) throws BaseException {
        User user = tokenService.getUserByToken();
        if (!user.getRole().equals(User.Role.SHOP))throw MainException.accessDenied();

        Long count = service.countByShop(user.getShop());

        List<TypeBuying> buying = service.findByShopAndPage(user.getShop(), page);
        List<BuyingResponse> responses = mapper.toBuyingResponse(buying);

        return new Response().ok2(MS,"buying",responses,"count",count);
    }
    public Object listByShop() throws BaseException {
        User user = tokenService.getUserByToken();
        if (!user.getRole().equals(User.Role.SHOP))throw MainException.accessDenied();

        List<TypeBuying> buying = service.findByShop(user.getShop());
        List<BuyingResponse> responses = mapper.toBuyingResponse(buying);

        return new Response().ok(MS,"buying",responses);
    }

    public Object createChild(BuyingListReq req) throws BaseException {

        User user = tokenService.getUserByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        TypeBuying typeBuying = this.getBuyingByShop(user, req.getBuyingId());

        service.saveChild(typeBuying, req.getName(), req.getPrice());
        return new Response().success("create success");
    }

    public Object deleteChild(Integer buyingId,Integer childId) throws BaseException {
        User user = tokenService.getUserByToken();

        TypeBuying buying = this.getBuyingByShop(user, buyingId);

        service.childExistsByIdAndBuying(buying,childId);

        service.deleteChildById(childId);

        return new Response().success("delete success");

    }

    public Object editChild(Integer id,BuyingListReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        TypeBuying buying = this.getBuyingByShop(user, req.getBuyingId());

        TypeBuyingList buyingList = service.childFindByIdAndBuying(buying, id);

        service.childEdit(buyingList, req.getName(), req.getPrice());

        return new Response().success("edit success");

    }
    public Object childByBuying(Integer id) throws BaseException {
        User user = tokenService.getUserByToken();
        TypeBuying buying = this.getBuyingByShop(user, id);

        List<TypeBuyingList> lists = service.childByBuyingId(buying);

        List<BuyingListResponse> responses = mapper.toBuyingListResponse(lists);
        return new Response().ok(MS,"buyingLists",responses);
    }

    //user shop buying
    public TypeBuying getBuyingByShop(User user,Integer buyingId) throws BaseException {
        if (!user.getRole().equals(User.Role.SHOP)) throw MainException.accessDenied();

        TypeBuying buying = service.findByIdAndShop(buyingId,user.getShop());

        return buying;
    }
}
