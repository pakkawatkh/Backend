package com.example.backend.api;

import com.example.backend.exception.BaseException;
import com.example.backend.model.adminModel.AUserActiveReq;
import com.example.backend.model.newsModel.NewsReq;
import com.example.backend.model.shopModel.ShopReq;
import com.example.backend.model.typeModel.TypeReq;
import com.example.backend.model.userModel.LoginReq;
import com.example.backend.model.userModel.UserEditReq;
import com.example.backend.process.business.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin")
public class AdminApi {
    private final ShopBusiness shopBusiness;
    private final TypeBusiness typeBusiness;
    private final UserBusiness userBusiness;
    private final OrderBusiness orderBusiness;
    private final TypeBuyingBusiness buyingBusiness;
    private final NewsBusiness newsBusiness;

    public AdminApi(ShopBusiness shopBusiness, TypeBusiness typeBusiness, UserBusiness userBusiness, OrderBusiness orderBusiness, TypeBuyingBusiness buyingBusiness, NewsBusiness newsBusiness) {
        this.shopBusiness = shopBusiness;
        this.typeBusiness = typeBusiness;
        this.userBusiness = userBusiness;
        this.orderBusiness = orderBusiness;
        this.buyingBusiness = buyingBusiness;
        this.newsBusiness = newsBusiness;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq req) throws BaseException {
        Object res = userBusiness.loginAdmin(req);

        return ResponseEntity.ok(res);
    }

    /*   SHOP    */
    @PutMapping("/shop/active/{id}")
    public ResponseEntity<Object> updateStatus(@PathVariable("id") Integer id, @RequestBody ShopReq req) throws BaseException {
        Object res = shopBusiness.changStatus(id, req);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/shop/list")
    public ResponseEntity<Object> shopList() throws BaseException {
        Object list = shopBusiness.list();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/shop/profile/{id}")
    public ResponseEntity<Object> shopById(@PathVariable("id") Integer id) throws BaseException {
        Object list = shopBusiness.byId(id);

        return ResponseEntity.ok(list);
    }

    /*   ORDER    */
    @GetMapping("/order/list")
    public ResponseEntity<Object> orderList() throws BaseException {
        Object orderAllUser = orderBusiness.getOrderAllUser();

        return ResponseEntity.ok(orderAllUser);
    }

    @GetMapping("/order/listByUser/{id}")
    public ResponseEntity<Object> orderListByUser(@PathVariable("id") String id) throws BaseException {
        Object orderByUser = orderBusiness.getOrderByUser(id);

        return ResponseEntity.ok(orderByUser);
    }

    /*   USER    */
    @PutMapping("/user/active/{id}")
    public ResponseEntity<Object> updateUserActive(@PathVariable("id") String id, @RequestBody AUserActiveReq req) throws BaseException {
        Object res = userBusiness.updateUserActive(id, req);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/list")
    public ResponseEntity<Object> userList() throws BaseException {
        Object users = userBusiness.userList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/profile/{id}")
    public ResponseEntity<Object> userById(@PathVariable("id") String id) throws BaseException {
        Object user = userBusiness.userById(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/profileByShop/{id}")
    public ResponseEntity<Object> userByShop(@PathVariable("id") Integer id) throws BaseException {
        Object user = userBusiness.userByShop(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/edit/{id}")
    public ResponseEntity<Object> userEditProfile(@PathVariable("id") String id, @RequestBody UserEditReq req) throws BaseException {
        Object res = userBusiness.editUserById(id, req);

        return ResponseEntity.ok(res);
    }

    /*   TYPE    */
    @GetMapping("/type/list")
    public ResponseEntity<Object> typeList() throws BaseException {
        Object list = typeBusiness.list();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/type/save")
    public ResponseEntity<Object> typeSave(@RequestBody TypeReq req) throws BaseException {
        Object res = typeBusiness.save(req);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/type/edit/{id}")
    public ResponseEntity<Object> typeEdit(@PathVariable("id") Integer id, @RequestBody TypeReq req) throws BaseException {
        Object res = typeBusiness.edit(id, req);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/type/delete/{id}")
    public ResponseEntity<Object> typeDelete(@PathVariable("id") Integer id) throws BaseException {
        Object res = typeBusiness.delete(id);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/type/recovery/{id}")
    public ResponseEntity<Object> typeRecovery(@PathVariable("id") Integer id) throws BaseException {
        Object res = typeBusiness.recovery(id);

        return ResponseEntity.ok(res);
    }

    /*   BUYING    */
    @GetMapping("/buying/listByShop/{id}")
    public ResponseEntity<Object> listByShop(@PathVariable("id") Integer id) throws BaseException {
        Object listByShop = buyingBusiness.getListByShopId(id);

        return ResponseEntity.ok(listByShop);
    }

    /*  NEWS  */
    @DeleteMapping("/news/delete/{id}")
    public ResponseEntity<Object> deleteNews(@PathVariable("id") Integer id) throws BaseException {
        Object res = newsBusiness.delete(id);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/news/save")
    public ResponseEntity<Object> saveNews(@RequestBody NewsReq req) throws BaseException {
        Object res = newsBusiness.save(req);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/news/edit/{id}")
    public ResponseEntity<Object> editNews(@PathVariable("id") Integer id, @RequestBody NewsReq req) throws BaseException {
        Object res = newsBusiness.edit(id, req);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/news/create")
    public Object createNews(@RequestParam(value = "file") MultipartFile[] file,
                             @RequestParam(value = "paragraphOne") String[] paragraphOne,
                             @RequestParam(value = "reference") String reference,
                             @RequestParam(value = "linkRef") String linkRef,
                             @RequestParam(value = "title") String title
    ) throws BaseException {
        ArrayList<String> paragraph = new ArrayList<>();
        for (String i :paragraphOne){
            paragraph.add(i);
        }
        String paragraphToString = paragraph.toString();

        NewsReq req = new NewsReq();
        req.setParagraphOne(paragraphToString);
        req.setReference(reference);
        req.setLinkRef(linkRef);
        req.setTitle(title);

        Object res = newsBusiness.create(file, req);

        return res;
    }
}
