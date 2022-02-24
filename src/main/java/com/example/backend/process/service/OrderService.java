package com.example.backend.process.service;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.OrderException;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.process.repository.OrdersRepository;
import com.example.backend.process.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrdersRepository repository;
    private final TypeRepository typeProductRepository;

    public OrderService(OrdersRepository repository, TypeRepository typeProductRepository) {
        this.repository = repository;
        this.typeProductRepository = typeProductRepository;
    }

    public void createOrder(User user, Type type, Float weight, String picture, Long latitude, Long longitude) throws BaseException {
        Orders entity = new Orders();
        entity.setStatus(Orders.Status.BUY);
        entity.setUser(user);
        entity.setType(type);
        entity.setPicture(picture);
        entity.setWeight(weight);
        entity.setLatitude(latitude);
        entity.setLongitude(longitude);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void changeStatus(Integer id, Orders.Status status, User user) throws BaseException {
        if (id == null || status == null) throw MainException.requestInvalid();

        Optional<Orders> orders = repository.findByIdAndUser(id, user);
        if (orders.isEmpty()) throw OrderException.orderNotFound();

        Orders entity = orders.get();
        entity.setStatus(status);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public List<Orders> findByUser(User user) {
        List<Orders> orders = repository.findByUser(user);
        BaseUrlFile urlFile = new BaseUrlFile();
        for (Orders order : orders)
            order.setPicture(urlFile.getDomain() + urlFile.getImageOrderUrl() + order.getPicture());

        return orders;
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
}
