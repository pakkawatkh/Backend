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

@RestController
@RequestMapping("/admin")
public class AdminApi {

    private final UserBusiness userBusiness;

    public AdminApi(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
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
}

@RestController
@RequestMapping("/admin/news")
class AdminNews {
    private final NewsBusiness business;

    AdminNews(NewsBusiness business) {
        this.business = business;
    }

    /*  NEWS  */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteNews(@PathVariable("id") Integer id) throws BaseException {
        Object res = business.delete(id);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveNews(@RequestBody NewsReq req) throws BaseException {
        Object res = business.save(req);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editNews(@PathVariable("id") Integer id, @RequestBody NewsReq req) throws BaseException {
        Object res = business.edit(id, req);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createNews(
            @RequestParam(value = "file") MultipartFile[] file,
            @RequestParam(value = "paragraph") String[] paragraph,
            @RequestParam(value = "reference") String reference,
            @RequestParam(value = "linkRef") String linkRef,
            @RequestParam(value = "title") String title
    ) throws BaseException {

        Object res = business.create(file, paragraph, reference, linkRef, title);

        return ResponseEntity.ok(res);
    }
}

@RestController
@RequestMapping("/admin/order")
class AdminOrder {

    private final OrderBusiness business;

    AdminOrder(OrderBusiness business) {
        this.business = business;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> orderList() throws BaseException {
        Object orderAllUser = business.getOrderAllUser();

        return ResponseEntity.ok(orderAllUser);
    }

    @GetMapping("/listByUser/{id}")
    public ResponseEntity<Object> orderListByUser(@PathVariable("id") String id) throws BaseException {
        Object orderByUser = business.getOrderByUser(id);

        return ResponseEntity.ok(orderByUser);
    }
}

@RestController
@RequestMapping("/admin/shop")
class AdminShop {
    private final ShopBusiness business;

    AdminShop(ShopBusiness business) {
        this.business = business;
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<Object> updateStatus(@PathVariable("id") Integer id, @RequestBody ShopReq req) throws BaseException {
        Object res = business.changStatus(id, req);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> shopList() throws BaseException {
        Object list = business.list();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Object> shopById(@PathVariable("id") Integer id) throws BaseException {
        Object list = business.byId(id);

        return ResponseEntity.ok(list);
    }
}

@RestController
@RequestMapping("/admin/type")
class AdminType {
    private final TypeBusiness business;

    AdminType(TypeBusiness business) {
        this.business = business;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> typeList() throws BaseException {
        Object list = business.list();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> typeSave(@RequestBody TypeReq req) throws BaseException {
        Object res = business.save(req);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> typeEdit(@PathVariable("id") Integer id, @RequestBody TypeReq req) throws BaseException {
        Object res = business.edit(id, req);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> typeDelete(@PathVariable("id") Integer id) throws BaseException {
        Object res = business.delete(id);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/recovery/{id}")
    public ResponseEntity<Object> typeRecovery(@PathVariable("id") Integer id) throws BaseException {
        Object res = business.recovery(id);

        return ResponseEntity.ok(res);
    }
}

