package com.example.backend.api;

import com.example.backend.process.business.OrderBusiness;
import com.example.backend.entity.Orders;
import com.example.backend.exception.BaseException;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderStatusReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderApi {

    private final OrderBusiness business;

    public OrderApi(OrderBusiness business) {
        this.business = business;
    }

    @PostMapping("/list")
    public ResponseEntity<Object> list() throws BaseException {
        Object list = business.listByUser();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderReq req) throws BaseException {
        Object order = business.create(req);

        return ResponseEntity.ok(order);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Object> cancel(@RequestBody OrderStatusReq req) throws BaseException {
        Object res = business.changeStatus(req, Orders.Status.CANCEL);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/success")
    public ResponseEntity<Object> success(@RequestBody OrderStatusReq req) throws BaseException {
        Object res = business.changeStatus(req, Orders.Status.SUCCESS);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/byId")
    public ResponseEntity<Object> byId(@RequestBody OrderReq req) throws BaseException {
        Object order = business.getById(req);

        return ResponseEntity.ok(order);
    }
}
