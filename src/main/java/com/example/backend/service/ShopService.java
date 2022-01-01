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
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    private final ShopRepository repository;

    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    public void saveShop(User user, String name, Long latitude, Long longitude) {

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
    }

    public void edit(User user, Integer id, String name, Long latitude, Long longitude) throws BaseException {

        Shop shop = this.checkShop(user, id);

        shop.setNumber(name);
        shop.setLatitude(latitude);
        shop.setLongitude(longitude);

        repository.save(shop);

    }

    public Shop checkShop(User user, Integer id) throws BaseException {

        Optional<Shop> shop = repository.findByIdAndUser(id, user);

        if (shop.isEmpty()) {
            throw ShopException.notId();
        }

        return shop.get();
    }

    public void changStatus(Integer id, Boolean status) throws BaseException {


        Shop shop = this.findById(id);

        shop.setActive(status);

        repository.save(shop);

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

    public void existsByName(String name) throws BaseException {
        boolean existsByName = repository.existsByName(name);

        if (existsByName){
            throw ShopException.nameDuplicate();
        }
    }

    public List<Shop> findAllByActive(){
        List<Shop> all = repository.findAllByActive(true);
        return all;
    }

    public Shop findByIdAndActive(Integer id) throws BaseException {
        Optional<Shop> shop = repository.findByIdAndActive(id,true);

        if (shop.isEmpty()){
            throw ShopException.notId();
        }
        return shop.get();
    }

}
