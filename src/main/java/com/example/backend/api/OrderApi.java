package com.example.backend.api;

import com.example.backend.process.business.OrderBusiness;
import com.example.backend.entity.Orders;
import com.example.backend.exception.BaseException;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderStatusReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderApi {

    private final OrderBusiness business;

    public OrderApi(OrderBusiness business) {
        this.business = business;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list() throws BaseException {
        Object list = business.listByUser();

        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderReq req) throws BaseException {
        Object order = business.create(req);

        return ResponseEntity.ok(order);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Object> cancel(@PathVariable("id") Integer id) throws BaseException {
        Object res = business.changeStatus(id, Orders.Status.CANCEL);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/success/{id}")
    public ResponseEntity<Object> success(@PathVariable("id") Integer id) throws BaseException {
        Object res = business.changeStatus(id, Orders.Status.SUCCESS);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Object> byId(@PathVariable("id") Integer id) throws BaseException {
        Object order = business.getById(id);

        return ResponseEntity.ok(order);
    }
}
