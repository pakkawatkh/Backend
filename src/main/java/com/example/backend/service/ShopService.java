package com.example.backend.service;

import com.example.backend.entity.Base.RandomString;
import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.ShopException;
import com.example.backend.model.Response;
import com.example.backend.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ShopService {

    private final ShopRepository repository;

    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    public Object saveShop(User user, String name, Long latitude, Long longitude) {

        String randomString = new RandomString().number();

        if (repository.existsByNumber(randomString)) {
            this.saveShop(user, name, latitude, longitude);
        }

        Shop entity = new Shop();

        entity.setUser(user);
        entity.setLongitude(longitude);
        entity.setLatitude(latitude);
        entity.setActive(true);
        entity.setDate(new Date());
        entity.setNumber("#" + randomString);
        entity.setName(name);

        repository.save(entity);
        return new Response().success("create success", null, null);
    }

    public Object edit(User user, Integer id, String name, Long latitude, Long longitude) throws BaseException {

        Shop shop = this.checkShop(user, id);

        shop.setNumber(name);
        shop.setLatitude(latitude);
        shop.setLongitude(longitude);

        repository.save(shop);

        return new Response().success("edit success", null, null);
    }

    public Shop checkShop(User user, Integer id) throws BaseException {

        Optional<Shop> shop = repository.findByIdAndUser(id, user);

        if (shop.isEmpty()) {
            throw ShopException.notId();
        }

        return shop.get();
    }

    public Object changStatus(Integer id, Boolean status) throws BaseException {


        Shop shop = this.findById(id);

        shop.setActive(status);

        repository.save(shop);

        return new Response().success("chang status success", null, null);
    }


    public Shop findById(Integer id) throws BaseException {

        Optional<Shop> shop = repository.findById(id);

        if (shop.isEmpty()) {
            throw ShopException.notId();
        }

        return shop.get();
    }

    public void existsByUser(User user) throws BaseException {
        boolean shop = repository.existsByUser(user);

        if (shop) {
            throw ShopException.registerError();
        }
    }

}
