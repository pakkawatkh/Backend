package com.example.backend.process.service;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.OrderException;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.model.orderModel.OrderBuyFillerReq;
import com.example.backend.model.orderModel.OrderRes;
import com.example.backend.process.repository.OrdersRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

interface statusTh {
    String BUY = "กำลังขาย";
    String SUCCESS = "สำเร็จ";
    String CANCEL = "ยกเลิก";
}

@Service
public class OrderService {

    private final OrdersRepository repository;

    public OrderService(OrdersRepository repository) {
        this.repository = repository;
    }

    public void createOrder(User user, Type type, String weight, String picture, String province, String district, String name, String detail, String price) throws BaseException {
        Orders entity = new Orders();
        entity.setStatus(Orders.Status.BUY);
        entity.setUser(user);
        entity.setType(type);
        entity.setPicture(picture);
        entity.setWeight(weight);
        entity.setDistrict(district);
        entity.setProvince(province);
        entity.setName(name);
        entity.setDetail(detail);
        entity.setPrice(price);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void update(Orders orders, Type type, String weight, String picture, String province, String district, String name, String detail, String price) throws MainException {

        orders.setStatus(Orders.Status.BUY);
        orders.setType(type);
        orders.setPicture(picture);
        orders.setWeight(weight);
        orders.setDistrict(district);
        orders.setProvince(province);
        orders.setName(name);
        orders.setDetail(detail);
        orders.setPrice(price);
        try {
            repository.save(orders);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void changeStatus(Integer id, Orders.Status status, User user) throws BaseException {

        Optional<Orders> orders = repository.findByIdAndUser(id, user);
        if (orders.isEmpty()) throw OrderException.orderNotFound();

        Orders entity = orders.get();

        if (!entity.getStatus().equals(Orders.Status.BUY)) throw OrderException.notAllowUpdate();

        entity.setStatus(status);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public List<Orders> findByUser(User user, int page) {
        PageRequest limit = PageRequest.of(page, 6);

        return repository.findAllByUserOrderByDateDesc(user, limit);
    }

    public List<Orders> findAllUserIsBuy(User user) {
        return repository.findAllByUserAndStatusOrderByDateDesc(user, Orders.Status.BUY);
    }

    public List<Orders> findAllByUser(User user) {

        return repository.findByUser(user);
    }

    public Long countByUser(User user) {
        return repository.count(user);
    }

    public List<Orders> findAll() {
        return repository.findAll();
    }

    public Orders findById(Integer id) {
        Optional<Orders> byId = repository.findById(id);

        return byId.get();
    }

    public Orders findByIdAndUser(Integer id, User user) throws BaseException {
        Optional<Orders> byId = repository.findByIdAndUser(id, user);
        if (byId.isEmpty()) throw OrderException.orderNotFound();

        return byId.get();
    }

    public boolean existsAllByType(Type type) {
        return repository.existsAllByType(type);
    }

    public void deleteById(Integer id, User user) throws BaseException {
        if (!repository.existsByIdAndUser(id, user)) throw OrderException.orderNotFound();

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public List<OrderRes> updateListOrder(List<OrderRes> orderRes) {
        BaseUrlFile urlFile = new BaseUrlFile();

        for (OrderRes order : orderRes) {
            if (order.getStatus().equals(Orders.Status.BUY)) order.setStatusTh(statusTh.BUY);
            else if (order.getStatus().equals(Orders.Status.SUCCESS)) order.setStatusTh(statusTh.SUCCESS);
            else order.setStatusTh(statusTh.CANCEL);

            order.setPictureUrl(urlFile.getDomain() + urlFile.getImageOrderUrl() + order.getPicture());

        }
        return orderRes;
    }

    public OrderRes updateOrder(OrderRes orderRes) {
        if (orderRes.getStatus().equals(Orders.Status.BUY)) orderRes.setStatusTh(statusTh.BUY);
        else if (orderRes.getStatus().equals(Orders.Status.SUCCESS)) orderRes.setStatusTh(statusTh.SUCCESS);
        else orderRes.setStatusTh(statusTh.CANCEL);
        BaseUrlFile urlFile = new BaseUrlFile();
        orderRes.setPictureUrl(urlFile.getDomain() + urlFile.getImageOrderUrl() + orderRes.getPicture());
        return orderRes;
    }


    //Filter
    public List<Orders> findAllByPage(Integer page, OrderBuyFillerReq.OrderBy orderBy, Orders.Status status) {
        PageRequest limit = PageRequest.of(page, 16);

        if (orderBy.equals(OrderBuyFillerReq.OrderBy.NEW)) {
            return repository.findAllByStatusOrderByDateDesc(status, limit);
        } else {
            return repository.findAllByStatusOrderByDateAsc(status, limit);
        }
    }

    public Long countAll(Orders.Status status) {
        return repository.countAllBuy(status);
    }

    //---------------------------------------//
    public List<Orders> findAllByPageAndType(Integer page, Type type, OrderBuyFillerReq.OrderBy orderBy, Orders.Status status) {
        PageRequest limit = PageRequest.of(page, 6);

        if (orderBy.equals(OrderBuyFillerReq.OrderBy.NEW)) {
            return repository.findAllByStatusAndTypeOrderByDateDesc(status, type, limit);
        } else {
            return repository.findAllByStatusAndTypeOrderByDateAsc(status, type, limit);
        }
    }

    public Long countAllByType(Type type, Orders.Status status) {
        return repository.countAllByType(type, status);
    }

    //---------------------------------------//
    public List<Orders> findAllByPageAndProvince(Integer page, OrderBuyFillerReq.OrderBy orderBy, String province, Orders.Status status) {
        PageRequest limit = PageRequest.of(page, 6);

        if (orderBy.equals(OrderBuyFillerReq.OrderBy.NEW)) {
            return repository.findAllByStatusAndProvinceOrderByDateDesc(status, province, limit);
        } else {
            return repository.findAllByStatusAndProvinceOrderByDateAsc(status, province, limit);
        }
    }

    public Long countAllByProvince(String province, Orders.Status status) {
        return repository.countAllByProvince(province, status);
    }

    //---------------------------------------//
    public List<Orders> findAllByPageAndProvinceAndType(Integer page, Type type, OrderBuyFillerReq.OrderBy orderBy, String province, Orders.Status status) {
        PageRequest limit = PageRequest.of(page, 6);

        if (orderBy.equals(OrderBuyFillerReq.OrderBy.NEW)) {
            return repository.findAllByStatusAndTypeAndProvinceOrderByDateDesc(status, type, province, limit);
        } else {
            return repository.findAllByStatusAndTypeAndProvinceOrderByDateAsc(status, type, province, limit);
        }
    }

    public Long countAllByProvinceAndType(Type type, String province, Orders.Status status) {
        return repository.countAllByTypeAndProvince(type, province, status);
    }
    //---------------------------------------//

    public List<Object> getProvince() {
        return repository.getProvince(Orders.Status.BUY);
    }

    public List<Orders> randomLimitAndStatus(Integer limit, String status) {
        return repository.randomByStatusLimit(status, limit);
    }

}