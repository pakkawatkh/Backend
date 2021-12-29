package com.example.backend.service;

import com.example.backend.entity.Shop;
import com.example.backend.entity.TypeBuying;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.TypeBuyingException;
import com.example.backend.model.Response;
import com.example.backend.repository.TypeBuyingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TypeBuyingService {


    public final TypeBuyingRepository repository;

    public TypeBuyingService(TypeBuyingRepository repository) {
        this.repository = repository;
    }

    public Object saveBuying(Shop shop,String name,Float price) throws BaseException {

        if (repository.existsByShopAndName(shop,name)){
            throw TypeBuyingException.nameDuplicate();
        }

        TypeBuying entity = new TypeBuying();

        entity.setName(name);
        entity.setDate(new Date());
        entity.setPrice(price);
        entity.setShop(shop);
        repository.save(entity);
        return new Response().success("create success",null,null);
    }
}
