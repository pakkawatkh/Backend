package com.example.backend.process.service;

import com.example.backend.entity.Base.RandomString;
import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.ShopException;
import com.example.backend.process.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    private final ShopRepository repository;

    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    public void saveShop(User user, String name, Long latitude, Long longitude) throws BaseException {
        String randomString = new RandomString().number();
        if (repository.existsByNumber(randomString)) this.saveShop(user, name, latitude, longitude);

        Shop entity = new Shop();
        entity.setUser(user);
        entity.setLongitude(longitude);
        entity.setLatitude(latitude);
        entity.setActive(true);
        entity.setDate(new Date());
        entity.setNumber("#" + randomString);
        entity.setName(name);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void edit(User user,  String name, Long latitude, Long longitude) throws BaseException {
        Optional<Shop> entity = repository.findByUser(user);

        if (entity.isEmpty())throw ShopException.notId();

        Shop shop = entity.get();
        shop.setNumber(name);
        shop.setLatitude(latitude);
        shop.setLongitude(longitude);
        try {
            repository.save(shop);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public Shop checkShop(User user, Integer id) throws BaseException {
        Optional<Shop> shop = repository.findByIdAndUser(id, user);
        if (shop.isEmpty()) throw ShopException.notId();

        return shop.get();
    }

    public void changStatus(Shop shop, Boolean status) throws BaseException {
        shop.setActive(status);
        try {
            repository.save(shop);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }


    public Shop findById(Integer id) throws BaseException {
        Optional<Shop> shop = repository.findById(id);
        if (shop.isEmpty()) throw ShopException.notId();

        return shop.get();
    }


    public void existsByUser(User user) throws BaseException {
        if (repository.existsByUser(user)) throw ShopException.registerError();
    }

    public void existsByName(String name) throws BaseException {
        if (repository.existsByName(name)) throw ShopException.nameDuplicate();
    }

    public List<Shop> findAllByActive() {
        return repository.findAllByActive(true);
    }

    public Shop findByIdAndActive(Integer id) throws BaseException {
        Optional<Shop> shop = repository.findByIdAndActive(id, true);
        if (shop.isEmpty()) throw ShopException.notId();

        return shop.get();
    }

    public List<Shop> findAll() {
        return repository.findAll();
    }


}
